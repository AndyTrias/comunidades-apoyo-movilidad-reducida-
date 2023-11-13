from django.shortcuts import render


def lista_incidentes(request):
    return render(request, "incidentes.html", {})


def incidente(request, id):
    return render(request, "incidente.html", {})
