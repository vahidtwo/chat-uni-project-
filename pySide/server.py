try:
    from PyAccessPoint import pyaccesspoint
    access_point = pyaccesspoint.AccessPoint()
    access_point.wlan='wlp2s0'
    access_point.ip = '192.168.43.1'
    access_point.netmask='255.255.255.0'
    access_point.ssid='chat test'
    access_point.password='HelloFromPy'
    access_point.start()
    if access_point.is_running():
        print ('hotspot start in '+str(access_point.ip)+
        ' with pass'+str(access_point.password))
    #access_point.stop()
except:
    access_point.stop()
    print('cant run accessPoint')
host = access_point.ip

import socket
import time
ipId={}
addr=None
sid=None
dId=None
msg=""
dataFromSocket=None
while(True):
    try:
        time.sleep(0.01)
        print('Ip List : '+str(ipId)+'\nmsg='+str(msg))
        port = 666

        with socket.socket(socket.AF_INET,socket.SOCK_STREAM) as s :
            s.bind((host,port))
            s.listen(10)
            print('listennig')
            conn,addr = s.accept()
            with conn:
                print("connected by:"+str(addr[0]))
                dataFromSocket = conn.recv(1024)
                print('massage from :'+str(addr[0])+' is : '+str(dataFromSocket))
                conn.close()
        badWord={'fuck','shutup','خفه شو','اشغال','آشغال'}
        if dataFromSocket != None :
            dataFromSocket=str(dataFromSocket).split(',')
            for txt in dataFromSocket :
                txt=txt.split('=')
                for t in txt : 
                    if t[0] == 'dId':
                        dId=t[1]
                    if t[0] == 'sId':
                        print ('sId:'+t[1])
                        sid=t[1]
                        ipId[sid]=addr[0]
                        print(ipId)
                    if t[0] == 'msg':
                        print ('msg:'+t[1])
                        msg=t[1]
            if msg!=None:
                for word in badWord :
                    msg.replace(word,"****",20)
            if msg!=None:
                with socket.socket(socket.AF_INET,socket.SOCK_STREAM) as s :
                    s.connect((ipId.get(dId),666))
                    s.send('msg:'+str(msg)+',id:'+str(id))
                    s.close()
        addr=None
        sid=None
        dId=None
        msg=None
        dataFromSocket=None
    except :
        access_point.stop()
        s.close()
        conn.close()
