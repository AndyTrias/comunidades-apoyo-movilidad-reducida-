def modify_file(filename):
    with open(filename, 'r') as f:
        lines = f.readlines()

    modified_lines = [f'"{line.strip()}",\n' for line in lines]

    with open(filename, 'w') as f:
        f.writelines(modified_lines)


modify_file('10000Peores.txt')