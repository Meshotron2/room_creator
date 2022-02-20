from PySide6 import QtWidgets

from comunication import http_requests
from widgets.main_page_utils.node_widget import NodeWidget

"""
{
  'nodes': [
    {
      'nodeId': 96,
      'cores': 6,
      'threads': 12,
      'cpu': 7.2161255,
      'totalRam': 33371951,
      'usedRam': 5558828,
      'temperature': [
        54.0,
        53.0,
        58.0,
        52.0,
        79.0,
        55.0
      ]
    }
  ],
  'processes': {
    '96': [
      {
        'nodeId': 96,
        'pid': 15478,
        'cpu': 0.0,
        'ram': 0,
        'progress': 0.0
      }
    ]
  }
}
"""


class StatusPageWidget(QtWidgets.QWidget):
    def __init__(self, server: str):
        super().__init__()

        data = http_requests.get_updates(server)

        print("BEGIN:", data)

        nodes = data["nodes"]
        processes = data["processes"]

        self.layout = QtWidgets.QVBoxLayout(self)

        for node in nodes:
            self.layout.addWidget(NodeWidget(node, processes[str(node['nodeId'])]))


if __name__ == '__main__':
    app = QtWidgets.QApplication([])

    widget = StatusPageWidget("http://localhost:8080/info")
    widget.resize(800, 600)
    widget.show()

    import sys

    sys.exit(app.exec())