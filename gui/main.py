# https://doc.qt.io/qtforpython/quickstart.html
# https://doc.qt.io/qtforpython/tutorials/index.html
# https://yewtu.be/watch?v=Jn0PpzB14Y8

import sys

from PySide6 import QtWidgets

from widgets.main_page import MainWidget

if __name__ == '__main__':
    app = QtWidgets.QApplication([])

    widget = MainWidget()
    widget.resize(800, 600)
    widget.show()

    sys.exit(app.exec())