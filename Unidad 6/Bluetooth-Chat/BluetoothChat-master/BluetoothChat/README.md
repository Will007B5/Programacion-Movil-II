
Bluetooth Chat Android
===================================

Este ejemplo muestra como implementar un chat sobre Bluetooth entre dos dispositivos android


Introduction
------------

Este ejemplo debería ejecutarse en dos dispositivos Android al mismo tiempo, para establecer una conversación bidireccional sobre
Bluetooth entre los dispositivos. Seleccione "Hecho descubrible" en el menú de desbordamiento de un dispositivo y haga clic en
en el icono de Bluetooth en el otro, para encontrar el dispositivo y establecer la conexión.

El ejempo demuestra lo siguiente usando la API Bluetooth

1. [Configurar][2] Bluetooth
2. [Escanear][3] en busca de dispositivos Bluetooth
3. [Consulta][4] del adaptador Bluetooth local para ver dispositivos Bluetooth emparejados
4. [Establecer RFCOMM][5] Mediante canales/sockets
5. [Conectar][6] a un dispositivo remoto
6. [Transferencia][7] de datos sobre Bluetooth

[1]: http://developer.android.com/guide/topics/connectivity/bluetooth.html
[2]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#Permissions
[3]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#FindingDevices
[4]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#QueryingPairedDevices
[5]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#ConnectingDevices
[6]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#ConnectingAsAClient
[7]: http://developer.android.com/guide/topics/connectivity/bluetooth.html#ManagingAConnection

Pre-requisitos
--------------

- Android SDK 27
- Android Build Tools v27.0.2
- Android Support Repository



Licencia
-------

Copyright 2017 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
