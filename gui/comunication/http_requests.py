import requests

url = "http://localhost:8080/info"


def get_updates():
    """
    With the help from <https://docs.python-requests.org/en/master/>
    """

    r = requests.get(url)
    print(r.json())


if __name__ == '__main__':
    get_updates()
