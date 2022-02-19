import requests


def get_updates(url: str) -> str:
    """
    With the help from <https://docs.python-requests.org/en/master/>
    """

    r = requests.get(url)
    json = r.json()
    print(json)
    return json


if __name__ == '__main__':
    get_updates("http://localhost:8080/info")
