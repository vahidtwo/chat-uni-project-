
from PyAccessPoint import pyaccesspoint
print( not pyaccesspoint.AccessPoint().is_running())
access_point =None
host=None
access_point = pyaccesspoint.AccessPoint()
access_point.wlan='wlp2s0'
if access_point.is_running():
    access_point.stop()
try:
    access_point.wlan='wlp2s0'
    access_point.ip = '192.168.43.1'
    access_point.netmask='255.255.255.0'
    access_point.ssid='chat test'
    access_point.password='HelloFromPy'
    access_point.start()
    host = access_point.ip
    print("accessPoint Starting...")
    if access_point.is_running():
        print ('hotspot start in '+str(access_point.ip)+' with pass'+str(access_point.password))
except:
    access_point.stop()
    print('cant run accessPoint')


import socket
import time
ipId={}
addr=None
sid=None
dId=""
msg=""
dataFromSocket=None
port = 16661
try:
    s= socket.socket(socket.AF_INET,socket.SOCK_STREAM) 
    s.bind(('',port))
    while(True):
        
        time.sleep(0.01)
        print('Ip List : '+str(ipId)+'\tmsg='+str(msg))

        conn=None
        print ('conn set null')
        try:
            print ('binding for port '+str(port))
            print('listennig')
            s.listen(20)
            print('wait for conn')
            conn,addr = s.accept()
            print("connected by: "+str(addr[0]))
            # ct = client_thread(conn)
            # ct.run()
            dataFromSocket = conn.recv(1024)
            print('massage from :'+str(addr[0])+' is : '+str(dataFromSocket))
            print('decoded massage from :'+str(addr[0])+' is : '+str(dataFromSocket.decode()))
            conn.close()
            print('conn closed')
            # s.close()
            # print('/////   socket closed    \ \ \ \ ')None
        except Exception as e :
            print(e)
            print('sonthing wrong in socket line 59')
            s.close()
            access_point.stop()
            exit(0)

        badWord={'fuck','shutup','خفه شو','اشغال','آشغال'}
        
        if dataFromSocket != None :
            print('in if and data'+str(dataFromSocket))
            dataFromSocket=str(dataFromSocket).split(',')
            for txt in dataFromSocket :
                t=txt.split(':')
                print ('----t0:'+t[0]+'    t1:'+t[1])
                if t[0] == 'dId':
                    print('-----'+str(t[0])+'=='+str(t[1]))
                    dId=t[1]
                    dId=dId.replace('\'','')
                elif t[0] == 'sId':
                    print('-----'+str(t[0])+'=='+str(t[1]))
                    sid=t[1]
                    print ('----- adding addr:'+addr[0])
                    ipId[sid]=addr[0]
                    print("-----ipId==="+str(ipId))
                elif t[0] == 'b\'msg':
                    print('-----'+str(t[0])+'=='+str(t[1]))
                    print ('msg==========='+t[1])
                    msg=t[1]
            if msg!=None:
                for word in badWord :
                    msg=msg.replace(word,"****",20)
                print('msg in replace==='+str(msg))
                a= socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                try:
                    print('connecting to did:'+dId+' with ip:'+str(ipId.get(dId)))
                    dip=str(ipId.get(dId))
                    a.connect_ex((dip,16661))
                    print('connected')
                    msg="msg:"+str(msg)+",sId:"+str(sid)+",dId:"+str(dId)
                    print(msg)
                    # a.send(bytes( msg,'utf-8'))
                    a.send( msg.encode())
                    
                    
                    print('***** send msg ****')
                    a.close()
                    print('***** a close *****')
                except Exception as e :
                    print(e)
                    print('somting wrong in line 99')
                    a.close()

        addr=None
        sid=None
        dId=None
        msg=None
        dataFromSocket=None
        print()
        print()
        print()
except Exception as e:
    print(e)
    s.close()
    access_point.stop()