from django.contrib import admin
from django.urls import path
import views

urlpatterns = [
    path("", views, name="lista_incidentes")
]
