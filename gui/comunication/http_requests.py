from typing import Any

import requests


def get_updates(url: str) -> Any:
    """
    With the help from <https://docs.python-requests.org/en/master/>
    """

    r = requests.get(url)
    json = r.json()
    print("From server:", json)
    return json


if __name__ == '__main__':
    get_updates("http://localhost:8080/info")
