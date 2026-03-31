# Prácticas Móviles - Android App (Práctica 1)

Este repositorio contiene una aplicación de práctica desarrollada para la plataforma Android. El proyecto está construido utilizando **Kotlin** y la moderna biblioteca de interfaz de usuario declarativa **Jetpack Compose**. 

El objetivo principal de esta "Práctica 1" es demostrar los conceptos fundamentales del desarrollo moderno en Android, incluyendo la creación de interfaces, el paso de datos entre pantallas y la configuración de un sistema de navegación.

##  Características Principales

* **Interfaz Declarativa:** Interfaces de usuario (UI) construidas 100% con Jetpack Compose, sin uso de XML tradicionales para los layouts.
* **Navegación Compose:** Implementación de rutas y navegación fluida entre diferentes pantallas (`HomeView` y `DetailsView`) utilizando `Navigation Compose`.
* **Paso de Parámetros:** Demostración de cómo transferir objetos o datos (modelo `Student`) a través de las rutas de navegación.
* **Theming Moderno:** Uso de temas personalizados (Colores, Tipografía y Formas) adaptados a Material Design.

## Stack Tecnológico

* **Plataforma:** Android
* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Navegación:** `androidx.navigation:navigation-compose`
* **Herramienta de Construcción:** Gradle (Kotlin DSL `build.gradle.kts`)

## Estructura del Código Source (`app/src/main/java/com/heber/practica1/`)

El código está organizado siguiendo buenas prácticas de separación de responsabilidades:

* `/views`: Contiene las pantallas principales de la aplicación (`HomeView.kt`, `DetailsView.kt`).
* `/navManager`: Aloja la configuración del grafo de navegación (`NavManager.kt`), definiendo las rutas y cómo se conectan las vistas.
* `/data`: Contiene los modelos de datos de la aplicación (ej. `Student.kt`).
* `/ui/theme`: Archivos de configuración de diseño global (`Color.kt`, `Theme.kt`, `Type.kt`).
* `MainActivity.kt`: El punto de entrada principal (Activity) que inicializa Compose y llama al `NavManager`.

##  Requisitos Previos

Para abrir, compilar y ejecutar este proyecto en tu entorno local, necesitas:

1. **Android Studio:** Se recomienda la versión más reciente (Iguana, Jellyfish o superior) con soporte completo para Jetpack Compose y Kotlin DSL.
2. **SDK de Android:** Configurado con la versión objetivo (Target SDK) especificada en los archivos de Gradle.
3. **Dispositivo/Emulador:** Un dispositivo físico Android conectado por USB/Wi-Fi o un Emulador de Android Virtual Device (AVD) configurado en Android Studio.

