import sys

import ezdxf

coordinate = (int, int, int)


class Node:

    def __init__(self, value, children: list):
        self.value = value
        self.children = children

    def __eq__(self, other):
        if isinstance(self.value, type(other)):
            return self.value == other
        if not isinstance(other, Node):
            return False
        return self.value == other.value

    def __str__(self):
        # return "[" + str(self.value) + "]: [" + str(["\n- " + str(n.value) for n in self.children]) + "]"
        # return f"[{str(self.value)}]: [{str(["""- {str(n.value)}""" for n in self.children])}]"

        s = "\t[" + str(self.value) + "]: [\n\t\t" + "\n\t\t".join(
            [f"- {str(n.value)}" for n in self.children]) + "\n\t] "
        return s


nodes: list[Node] = []


def link(a: coordinate, b: coordinate):
    n1 = Node(a, [])
    n2 = Node(b, [])

    found_n1, found_n2 = False, False
    for i in range(len(nodes)):
        if nodes[i].value == a:
            n1 = nodes[i]
            found_n1 = True
        elif nodes[i].value == b:
            n2 = nodes[i]
            found_n2 = True

    if not found_n1:
        nodes.append(n1)
    if not found_n2:
        nodes.append(n2)

    if n1 not in n2.children and n2 not in n1.children:
        n1.children.append(n2)


def clean() -> list[list[coordinate]]:
    to_rm = []




def agregate() -> list[list[coordinate]]:
    l = nodes.copy()
    removed = True
    while removed:
        removed = False
        for v in l:
            if len(v.children) < 3:
                removed = True
                l.remove(v)

    return l


def tests():
    try:
        doc = ezdxf.readfile(sys.argv[1])
        msp = doc.modelspace()

        lines = msp.query("LINE").groupby("color").get(1, [])

        for l in lines:
            print(l)
    except IOError:
        print(f"Not a DXF file or a generic I/O error.")
        sys.exit(1)
    except ezdxf.DXFStructureError:
        print(f"Invalid or corrupted DXF file.")
        sys.exit(2)


def create_room():
    """
    # 3D entities
        - Face3D https://ezdxf.readthedocs.io/en/stable/dxfentities/3dface.html
            + `{"vertex0": int, "vertex1": int, "vertex2": int, "vertex3": int}`
        - Mesh https://ezdxf.readthedocs.io/en/stable/dxfentities/mesh.html
            + `{"vertices": list[(int, int)], "edges": list[(int, int)]}`
        - Solid https://ezdxf.readthedocs.io/en/stable/dxfentities/solid.html
            + `{"vertex0": int, "vertex1": int, "vertex2": int, "vertex3": int}`
        - PolyFace https://ezdxf.readthedocs.io/en/stable/dxfentities/polyline.html#polymesh
            + `{"vertices": list[int]}`
    """
    pass


if __name__ == '__main__':
    class Cube:
        def __init__(self, a: coordinate, b: coordinate, c: coordinate, d: coordinate, e: coordinate, f: coordinate,
                     g: coordinate, h: coordinate):
            link(a, d)
            link(a, b)
            link(a, f)

            link(b, a)
            link(b, c)
            link(b, g)

            link(c, b)
            link(c, d)
            link(c, h)

            link(d, a)
            link(d, c)
            link(d, e)

            link(e, d)
            link(e, f)
            link(e, h)

            link(f, a)
            link(f, e)
            link(f, g)

            link(g, f)
            link(g, b)
            link(g, h)

            link(h, c)
            link(h, e)
            link(h, g)

        def __str__(self):
            return "Cube {\n" + "\n\t".join([str(n) for n in nodes]) + "\n}"


    link((1, 0, 1), (-1, -1, -1))

    Cube(
        (1, 0, 1),  # a
        (1, 1, 1),  # b
        (0, 1, 1),  # c
        (0, 0, 1),  # d
        (0, 0, 0),  # e
        (1, 0, 0),  # f
        (1, 1, 0),  # g
        (0, 1, 0)  # h
    )

    l = agregate()
    print("\n\t".join([str(n) for n in l]))
