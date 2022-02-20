from PySide6 import QtWidgets

from widgets.creation_wizard_utils.labeled_widgets import InfoWidget

# class LayoutWrapper(QtWidgets.QLayoutItem):
#     def __init__(self, widget: QtWidgets.QWidget):
#         super().__init__()
#         self.widget = widget
from widgets.main_page_utils.process_widget import ProcessWidget


class NodeWidget(QtWidgets.QWidget):
    def __init__(self, node_dict: dict, proc_dicts: list[dict]):
        super().__init__()
        print("Node:", node_dict)

        self.layout = QtWidgets.QVBoxLayout(self)

        # Node info
        self.info_layout = QtWidgets.QGridLayout(self)

        # byte nodeId, int cores, int threads,
        # float cpu, long totalRam, int usedRam,
        # float[] temperature
        self.info_layout.addWidget(InfoWidget("id: ", str(node_dict["nodeId"])), 0, 0, 1, 1)
        self.info_layout.addWidget((InfoWidget("cores: ", str(node_dict["cores"]))), 0, 1, 1, 1)
        self.info_layout.addWidget((InfoWidget("threads: ", str(node_dict["threads"]))), 0, 2, 1, 1)

        self.info_layout.addWidget(InfoWidget("cpu: ", str(node_dict["cpu"])), 1, 0, 1, 1)
        self.info_layout.addWidget(InfoWidget("total ram: ", str(node_dict["totalRam"])), 1, 1, 1, 1)
        self.info_layout.addWidget(InfoWidget("used ram: ", str(node_dict["usedRam"])), 1, 2, 1, 1)

        self.info_layout.addWidget(InfoWidget("temperatures: ", str(node_dict["temperature"])), 2, 0, 1, 1)

        self.layout.addLayout(self.info_layout)

        # Process widgets
        self.proc_layout = QtWidgets.QHBoxLayout(self)

        print("DBG", type(proc_dicts), proc_dicts)
        for pd in proc_dicts:
            # for k in pd.keys():
            #     pd = pd[k]
            #     break

            self.proc_layout.addWidget(ProcessWidget(pd))

        self.layout.addLayout(self.proc_layout)


if __name__ == '__main__':
    app = QtWidgets.QApplication([])

    widget = NodeWidget({"nodeId": 0, "cores": 6, "threads": 12, "cpu": 10, "totalRam": 32012341234, "usedRam": 124334,
                         "temperature": [31, 32, 33, 34, 35, 36]},
                        [  # byte nodeId, int pid, float cpu, int ram, float progress
                            {"nodeId": 0, "pid": 420, "cpu": 40, "ram": 50, "progress": 69},
                            {"nodeId": 0, "pid": 500, "cpu": 46, "ram": 40, "progress": 80}
                        ])
    widget.resize(800, 600)
    widget.show()

    import sys

    sys.exit(app.exec())
