"""
With the help of
https://www.tutorialspoint.com/python3/python_networking.htm
"""

import socket


def send(msg: str, wait: bool = False):
    print(msg)

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    host = "127.0.0.1"
    port = 9999

    s.connect((host, port))

    s.send((msg + '\n').encode('utf-8'))

    if wait:
        reply = s.recv(1024)

    s.close()

    if wait:
        decoded = reply.decode('utf-8')
        print(decoded)
        return decoded
