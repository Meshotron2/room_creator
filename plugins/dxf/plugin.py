import sys
import json

import ezdxf
from ezdxf.entities import DXFEntity, Face3d

import numpy as np
from itertools import combinations

from scipy.spatial import Delaunay

import mesh_raycast

triangle = (int, int, int)


def touch(t1, t2):
    cnt = 0
    for v1 in t1:
        for v2 in t2:
            if v1 == v2:
                cnt += 1
    return cnt == 2


def buildHull(hull, triangles, t):
    for tri in findAllTriaglesThatTouch(t, triangles):
        if tri not in hull:
            hull.add(tri)
            buildHull(hull, triangles, tri)


def buildHulls(triangles: list):
    triangles_cpy = triangles.copy()

    hulls = []
    while len(triangles_cpy) > 0:
        hull = set()
        buildHull(hull, triangles_cpy, triangles_cpy[0])

        hulls.append(hull)

        for v in hull:
            triangles_cpy.remove(v)

    return hulls

    # hull = set()
    # buildHull(hull, triangles, triangles[0])
    # print(hull)
    # print(len(hull))

    # hull2 = set()
    # buildHull(hull2, triangles, triangles[23])
    # print(hull2)
    # print(len(hull2))


def findAllTriaglesThatTouch(tri, triangles):
    touching = []

    for t in triangles:
        if tri != t:
            if touch(tri, t):
                touching.append(t)
    return touching


def in_hull(p, hull):
    """
    Test if points in `p` are in `hull`

    `p` should be a `NxK` coordinates of `N` points in `K` dimensions
    `hull` is either a scipy.spatial.Delaunay object or the `MxK` array of the 
    coordinates of `M` points in `K`dimensions for which Delaunay triangulation
    will be computed
    """
    if not isinstance(hull, Delaunay):
        hull = Delaunay(hull)

    return hull.find_simplex(p) >= 0


def process_3d_face(e: Face3d):
    if e.dxf.vtx2 != e.dxf.vtx3:  # not a triangle
        return (e.dxf.vtx0, e.dxf.vtx1, e.dxf.vtx2), (e.dxf.vtx0, e.dxf.vtx2, e.dxf.vtx3)
    else:
        return (e.dxf.vtx0, e.dxf.vtx1, e.dxf.vtx2)


def process_entity(e: DXFEntity):
    if e.dxftype() == "3DFACE":
        return process_3d_face(e)


def read_room(room: str):
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
    try:
        doc = ezdxf.readfile(room)
    except IOError:
        print(f"Not a DXF file or a generic I/O error.")
        sys.exit(1)
    except ezdxf.DXFStructureError:
        print(f"Invalid or corrupted DXF file.")
        sys.exit(2)

    msp = doc.modelspace()
    triangles: list[triangle] = []
    for e in msp:
        res = process_entity(e)
        if len(res) == 2:
            triangles.append(res[0])
            triangles.append(res[1])
        else:
            triangles.append(res)

    hulls = buildHulls(triangles)

    return hulls
    # print(hulls)
    #
    # for shape in hulls:
    #     shape_arr = [vec for vec in shape]
    #     # print("---")
    #     # print(shape_arr)
    #     # print("---")
    #     mesh = np.array(shape_arr, dtype='f4')
    #     strt = -3
    #     step = 0.1
    #     for x in [strt + (x * step) for x in range(0, int(3 / step))]:
    #         for y in [strt + (x * step) for x in range(0, int(3 / step))]:
    #             for z in [strt + (x * step) for x in range(0, int(3 / step))]:
    #                 result = mesh_raycast.raycast(source=(x, y, z), direction=(0.0, 0.0, -1.0),
    #                                               mesh=mesh)
    #                 # ímpar => dentro
    #                 # print(f"({str(x)[:5]},{str(y)[:5]},{str(z)[:5]})\t{(len(result) % 2 != 0)}")
    #                 assert (len(result) % 2 != 0) == (-1 <= x <= 1 and -1 <= z < 1 and -1 <= y < 1)
    # sys.exit(1)


if __name__ == '__main__':
    args = sys.argv

    if args[1] == 'lm':
        hulls = read_room("cube.dxf")

        shapes = {}
        i = 0
        for shape in hulls:
            s_name = f"shape_{i}"
            shapes[s_name] = {
                "coefficient": 0
            }

            i1 = 0
            for triangle in shape:
                pt0 = [triangle[0][0], triangle[0][1], triangle[0][2]]
                pt1 = [triangle[1][0], triangle[1][1], triangle[1][2]]
                pt2 = [triangle[2][0], triangle[2][1], triangle[2][2]]

                shapes[s_name][f'tri_{i1}'] = [pt0, pt1, pt2]
                i1 += 1

            i += 1

        data = {
            "xg": "",
            "yg": "",
            "zg": "",
            "f": "",
            "file": "./dwm/plugin.dwm",
            "shapes": shapes
        }

        print(str(data))

    elif args[1] == 'dwm':
        print(''.join(args[2:]))
        shapes = json.loads(''.join(args[2:]))['shapes']

        for k in shapes:
            triangles = []
            coef = shapes[k]['coefficient']
            for k1 in shapes[k]:
                if k1 != 'coefficient':
                    triangles.append(shapes[k][k1])
            mesh = np.array(triangles, dtype='f4')
            # print(mesh)

            strt = -2
            step = 0.2
            for x in [strt + (x * step) for x in range(0, int(2 / step))]:
                for y in [strt + (x * step) for x in range(0, int(2 / step))]:
                    for z in [strt + (x * step) for x in range(0, int(2 / step))]:
                        result = mesh_raycast.raycast(source=(x, y, z), direction=(0.0, 0.0, -1.0),
                                                      mesh=mesh)
                        # ímpar => dentro
                        # print(f"({str(x)[:5]},{str(y)[:5]},{str(z)[:5]})\t{(len(result) % 2 != 0)}")
                        if not (len(result) % 2 != 0) == (-1 <= x <= 1 and -1 <= z < 1 and -1 <= y < 1):
                            print(f"({str(x)[:5]},{str(y)[:5]},{str(z)[:5]})\t{(len(result) % 2 != 0)}")

                        # if len(result) % 2 != 0:
                        #     print(coef)