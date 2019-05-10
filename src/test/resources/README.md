# TestDirectripsViajeroWeb
# ========================

## OBJETIVO

El objetivo de este proyecto es el de automatizar las pruebas funcionales con Selenium

## ORGANIZACIÓN

El proyecto tiene **3 formas** de crear las pruebas funcionales:

* Con código _hardcore_

```
Ver clase TC4AutomaticOld
```

* Con código usando un archivo de propiedades (_config/config.properties_) para cambiar los valores que son entrada para realizar las pruebas

```
Ver clase TC4Automatic

```

* Con código usando un DataProvider específicamente Excel (_config/TestData.xlsx_) para cambiar los valores que son entrada para realizar las pruebas y haciendo uso de una bitácora por consola y por archivo.

```
Ver clase TC4AutomaticDataProvider
```


_El archivo **testng.xml** es el que contiene la suite de pruebas_

Las pruebas automatizadas se encuentran en la carpeta _src/test/java/directripsViajeroWeb_

## HERRAMIENTAS
_Las herramientas que se usaron para realizar estas pruebas funcionales fueron:_
* [Selenium IDE](http://docs.seleniumhq.org/download/)
* [FireFox Ver 54](https://www.askmetutorials.com/2017/06/install-firefox-54-on-ubuntu-1604-1404.html)

 