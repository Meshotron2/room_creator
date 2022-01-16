"""
With the help of
https://www.tutorialspoint.com/python3/python_networking.htm
"""

import socket


def send(msg: str):
    print(msg)

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    host = "127.0.0.1"
    port = 9999

    s.connect((host, port))

    s.send(msg.encode('utf-8'))

    s.close()
