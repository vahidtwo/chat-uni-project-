from PyAccessPoint import pyaccesspoint
import socket
import json
import time
from .dbconn import trust_user_pass, get_filter_word
print('hot spot status is {}'.format(not pyaccesspoint.AccessPoint().is_running()))
access_point = None
host = None
access_point = pyaccesspoint.AccessPoint()
access_point.wlan = 'wlp2s0'
if access_point.is_running():
    access_point.stop()
try:
    access_point.wlan = 'wlp2s0'
    access_point.ip = '192.168.43.1'
    access_point.netmask = '255.255.255.0'
    access_point.ssid = 'chat test'
    access_point.password = 'uniproject'
    access_point.start()
    host = access_point.ip
    print("accessPoint Starting...")
    if access_point.is_running():
        print('hotspot start in '+str(access_point.ip)+' with pass'+str(access_point.password))
except Exception as e:
    access_point.stop()
    print('cant run accessPoint')

ipId = {}
addr = None
sid = None
dId = ""
msg = ""
dataFromSocket = None
port = 16661
try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('', port))
    while(True):
        time.sleep(0.01)
        print('Ip List : '+str(ipId)+'\tmsg='+str(msg))
        conn = None
        print('conn set null')
        try:
            print('binding for port '+str(port))
            print('listennig')
            s.listen(20)
            print('wait for conn')
            conn, addr = s.accept()
            print("connected by: "+str(addr[0]))
            # ct = client_thread(conn)
            # ct.run()
            dataFromSocket = conn.recv(1024).decode('utf-8')
            json_data_from_socket = json.loads(dataFromSocket)
            print('massage from :'+str(addr[0])+' is : '+str(dataFromSocket))
            print('decoded massage from :'+str(addr[0])+' is : '+str(dataFromSocket))
            conn.close()
            print('conn closed')
            # s.close()
            # print('/////   socket closed    \ \ \ \ ')None
        except Exception as e:
            print(e)
            s.close()
            access_point.stop()
            continue
        badWord = get_filter_word()
        if json_data_from_socket is not None:
            print('in if and data'+str(json_data_from_socket))
            user = json_data_from_socket['username']
            password = json_data_from_socket['password']
            res = trust_user_pass(user, password)
            if not res:
                continue
            dId = json_data_from_socket['dId']
            sid = json_data_from_socket['sId']
            ipId[sid] = addr[0]
            print("ipId==="+str(ipId))
            msg = json_data_from_socket['msg']
            print('msg ==='+msg)
            if msg is not None:
                for word in badWord:
                    msg = msg.replace(word, "****", 20)
                print('msg in replace ==='+str(msg))
                a = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                try:
                    print('connecting to did:'+dId+' with ip:'+str(ipId.get(dId)))
                    dip = str(ipId.get(dId))
                    a.connect_ex((dip, 16661))
                    print('connected')
                    msg = json.dumps({
                        "msg": msg,
                        "sId": sid,
                        "dId": dId
                    })
                    print(msg)
                    # a.send(bytes( msg,'utf-8'))
                    a.send(msg.encode('utf-8'))
                    print('***** send msg ****')
                    a.close()
                    print('***** a close *****')
                except Exception as e:
                    print(e)
                    a.close()
        addr = None
        sid = None
        dId = None
        msg = None
        dataFromSocket = None
        json_data_from_socket = None
        print()
        print()
        print()
except Exception as e:
    print(e)
    s.close()
    access_point.stop()
