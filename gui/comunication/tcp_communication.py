import json
import socket


def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    host = "127.0.0.1"
    port = 9999

    s.connect((host, port))
    msg = s.recv(1024)
    msg = msg.decode('ascii')
    validation = msg_is_valid(msg)
    if (validation[0]):
        valid = "Message is valid"
        s.send(valid.encode('ascii'))

    else:
        error = "Message is not valid"
        s.send(error.encode('ascii'))

    s.close()
    return [validation[1], msg]


def msg_is_valid(msg):
    try:
        m = json.loads(msg)  # Check if message is in json format

        if ("pid" and "cpu" and "ram" and "progress" in m):  # Check if message is ProcData type
            return [True, "ProcData"]
        elif "cores" and "threads" and "cpu_usage" and "total_ram" and "used_ram" and "temperature" in m:  # Check if message is NodeData type
            return [True, "NodeData"]
        else:
            return [False, ""]
    except:
        return [False, ""]


if __name__ == "__main__":
    main()
