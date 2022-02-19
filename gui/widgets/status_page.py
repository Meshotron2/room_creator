import json

from PySide6 import QtWidgets

from comunication import http_requests


class MainWidget(QtWidgets.QWidget):
    def __init__(self, server: str):
        super().__init__()

        data = json.loads(http_requests.get_updates(server))

        nodes = data["nodes"]
        processes = data["processes"]

        self.layout = QtWidgets.QVBoxLayout(self)


