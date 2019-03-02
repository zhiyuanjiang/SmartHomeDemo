wifi.sta.sethostname("Node-MCU")
wifi.setmode(wifi.STATION)
print(wifi.sta.gethostname(),"\n")

--自动连接wifi
station_cfg={}
station_cfg.ssid="520"
station_cfg.pwd="z520520520"
station_cfg.save=false
wifi.sta.config(station_cfg)
wifi.sta.connect()

url = "http://192.168.1.105:8080/SmartHomeDemo/SensorData_servlet"
sensorDataJson = nil
state = true

--启动定时器发送传感器数据
tmr.alarm(1, 4000, tmr.ALARM_AUTO, function()
    if wifi.sta.getip() == nil then
        print('Waiting for connectting IP ...')
    else if state and getSensorData() then
        print('IP is ' .. wifi.sta.getip())
        print(sensorDataJson)
        state = false
        sendData(url, sensorDataJson)
        --tmr.stop(1)
    end
    end
end)

-- 获取传感器数据
function getSensorData()
pin = 8
status, temp, humi, temp_dec, humi_dec = dht.read(pin)
if status == dht.OK then
    -- Integer firmware using this example
    --print(string.format("DHT Temperature:%d.%03d;Humidity:%d.%03d\r\n",
    --      math.floor(temp),
    --      temp_dec,
    --      math.floor(humi),
    --      humi_dec
    --))

    data = {}
    data.id = "12345678"
    data.ip = wifi.sta.getip()
    data.temp = temp
    data.humi = humi
    sensorDataJson = sjson.encode(data)
    
    return true
else
    print('sensor data read failed\r\n')
    return false
end
end

-- 发送http链接
function sendData(url, st)
http.post(url,
  'Content-Type: application/json\r\n',
  st,
  function(code, data)
    state = true
    if (code < 0) then
      print("HTTP request failed\r\n")
    else
      print(code, data, '\r\n')
    end
  end)
end

-- 监听80端口
sv = net.createServer(net.TCP, 30)

function receiver(sck, data)
  print(data)
  changeLight()
  sck:close()
end

if sv then
  sv:listen(80, function(conn)
    conn:on("receive", receiver)
    conn:send("hello world")
  end)
end

function changeLight()
    gpio.mode(4, gpio.OUTPUT)
    light = gpio.read(4)
    if (light == 1) then
        gpio.write(4, gpio.LOW)
    else
        gpio.write(4, gpio.HIGH)
    end
end
