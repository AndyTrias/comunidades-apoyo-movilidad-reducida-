def sort_words(filename):
    with open(filename, 'r') as file:
        words = file.read().splitlines()
    words.sort()
    with open(filename, 'w') as file:
        for word in words:
            file.write(word + '\n')

sort_words("10000Peores.txt")
