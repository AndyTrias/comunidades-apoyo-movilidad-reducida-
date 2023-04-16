def remove_quotes_and_commas(filename):
    with open(filename, 'r') as file:
        lines = file.readlines()
    with open(filename, 'w') as file:
        for line in lines:
            new_line = line.replace('"', '').replace(',', '')
            file.write(new_line)


remove_quotes_and_commas("10000Peores.txt")