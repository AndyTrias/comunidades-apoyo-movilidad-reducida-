from django.shortcuts import render


def lista_incidentes(request):
    return render(request, "incidentes.html", {})
