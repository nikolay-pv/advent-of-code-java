from typing import List, Tuple
from scipy.optimize import linprog


def as_int_list(input: str) -> List[int]:
    return [int(x) for x in input.strip("\n()\{\}").split(",")]


def as_linprog(line: str) -> Tuple[List[int], List[List[int]], List[int]]:
    parts = line.split(" ")
    buttons_count = len(parts) - 2  # 2 for configs
    c = [1] * buttons_count
    b = as_int_list(parts[-1])
    # every button is a column
    A = [[0 for _ in range(buttons_count)] for _ in range(len(b))]
    for i in range(buttons_count):
        button = as_int_list(parts[i + 1])
        for j in button:
            A[j][i] = 1
    return (c, A, b)


if __name__ == "__main__":
    lines = open("input.txt", "r").readlines()
    total = 0
    for line in lines:
        (c, A, b) = as_linprog(line)
        res = linprog(c=c, A_eq=A, b_eq=b, integrality=1)
        total += sum(res.x)
    print(int(total))
