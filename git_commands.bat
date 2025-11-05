@echo off
echo Navegando al directorio del proyecto...
cd /d C:\Users\1097514221\AndroidStudioProjects\Practica2

echo Verificando estado del repositorio...
git status

echo Añadiendo archivos al staging area...
git add .

echo Haciendo commit de los cambios...
git commit -m "Añadir aplicacion contador con ViewModel"

echo Creando y cambiando al branch Contador...
git checkout -b Contador

echo Subiendo al repositorio remoto...
git push -u origin Contador

echo Proceso completado!
pause
