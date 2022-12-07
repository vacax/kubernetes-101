# Curso básico de Kubernetes para principiantes - Kubernetes-101

## Prerrequistos

Es necesario contar con una máquina virtual en un sistema operativo en Linux, 
preferiblemente Ubuntu en su última versión. 

## Conceptos Básicos

Kubernetes es una plataforma portable y extensible de código abierto para administrar cargas de trabajo y servicios. Kubernetes facilita la automatización y la configuración declarativa.
Tiene un ecosistema grande y en rápido crecimiento. El soporte, las herramientas y los servicios para Kubernetes están ampliamente disponibles.

Google liberó el proyecto Kubernetes en el año 2014. Kubernetes se basa en la experiencia de Google corriendo aplicaciones
en producción a gran escala por década y media, junto a las mejores ideas y prácticas de la comunidad. [Ver fuente](https://kubernetes.io/es/docs/concepts/overview/what-is-kubernetes/)

### ¿Por qué necesitamos Kubernetes (K8s)?

Kubernetes ofrece un entorno de administración centrado en **contenedores**. 
Kubernetes orquesta la infraestructura de cómputo, redes y almacenamiento para que las cargas de trabajo de los usuarios no tengan que hacerlo.
Esto ofrece la simplicidad de las Plataformas como Servicio (PaaS) con la flexibilidad de la Infraestructura como Servicio (IaaS) 
y permite la portabilidad entre proveedores de infraestructura. Representa una abstracción completa de nuestros componentes
de hardware utilizando contenedores.

### ¿Por qué usar contenedores?.

Desplegar contenedores basados en virtualización a nivel del sistema operativo, en vez del hardware.
Estos contenedores están aislados entre ellos y con el servidor anfitrión: tienen sus propios sistemas de archivos,
no ven los procesos de los demás y el uso de recursos puede ser limitado. 
Son más fáciles de construir que una máquina virtual, y porque no están acoplados a la infraestructura y sistema de archivos del anfitrión,
pueden llevarse entre nubes y distribuciones de sistema operativo.

Los beneficios de usar contenedores incluyen:

* Ágil creación y despliegue de aplicaciones: Mayor facilidad y eficiencia al crear imágenes de contenedor en vez de máquinas virtuales
* Desarrollo, integración y despliegue continuo: Permite que la imagen de contenedor se construya y despliegue de forma frecuente y confiable, facilitando los rollbacks pues la imagen es inmutable
* Separación de tareas entre Dev y Ops: Puedes crear imágenes de contenedor al momento de compilar y no al desplegar, desacoplando la aplicación de la infraestructura
* Observabilidad No solamente se presenta la información y métricas del sistema operativo, sino la salud de la aplicación y otras señales
* Consistencia entre los entornos de desarrollo, pruebas y producción: La aplicación funciona igual en un laptop y en la nube
* Portabilidad entre nubes y distribuciones: Funciona en Ubuntu, RHEL, CoreOS, tu datacenter físico, Google Kubernetes Engine y todo lo demás
* Administración centrada en la aplicación: Eleva el nivel de abstracción del sistema operativo y el hardware virtualizado a la aplicación que funciona en un sistema con recursos lógicos
* Microservicios distribuidos, elásticos, liberados y débilmente acoplados: Las aplicaciones se separan en piezas pequeñas e independientes que pueden ser desplegadas y administradas de forma dinámica, y no como una aplicación monolítica que opera en una sola máquina de gran capacidad
* Aislamiento de recursos: Hace el rendimiento de la aplicación más predecible
* Utilización de recursos: Permite mayor eficiencia y densidad

### Componentes de Kubernetes

![Componentes Kubenertes](/imagenes/componentes-k8s.png)

Un clúster Kubernetes consta de los componentes que representan el plano de control (nodo maestro) y un conjunto de máquinas denominadas nodos (nodos de trabajo).
Al desplegar Kubernetes, obtendrá un clúster.

Un clúster Kubernetes consta de un conjunto de máquinas de trabajo,
denominadas nodos, que ejecutan aplicaciones contenerizadas. Cada clúster contiene al menos un nodo de trabajo.

El o los nodos de trabajo alojan los POD que son los componentes de la carga de trabajo de la aplicación.
El plano de control gestiona los nodos de trabajo y los POD del clúster. [Ver fuente](https://www.ibm.com/docs/es/control-desk/7.6.1.x?topic=kubernetes-components) 

### ¿Qué son los Pods?

* Los Pods es la unidad más básica para ejecutar nuestras aplicaciones, representan la aplicación y contiene uno o varios contenedores o volúmenes de disco.
* La idea de tener más de un contenedor es que podemos combinar con otros servicios que están fuera de nuestra aplicación, como pueden ser agregadores de logs, filtrado de contenido, entre otros.
* Todos los contenedores dentro de un pod, comparten la misma dirección IP, simplemente es decir localhost y puerto del servicio del pods que está trabajando.
* La dirección que reciben es privada al cluster, la comunicación se realiza mediante el uso de servicios.
* Como kubernetes es una abstracción de los recursos, no es necesarios tener la cantidad de servidores. Cada pod tiene su propio ciclo de vida, lo ideal es tener una aplicación para cada pods. 

## Instalación de nuestro cluster

Para nuestro laboratorio estaremos trabajando con versiones certificadas de Kubernetes para trabajar de forma local.
No todas las funcionalidades están disponibles pero son muy buenas herramientas para realizar pruebas y ser ocupadas
en un ambiente de desarrollo. Las distribuciones que podemos ocupar son:

* [Minikube](https://minikube.sigs.k8s.io/docs/)
* [Kind](https://kind.sigs.k8s.io/)
* [K3s](https://k3s.io/)
* [Microk8s](https://microk8s.io)
* [K0s](https://k0sproject.io/)
* [K3d](https://k3d.io/)

Para probar nuestros ejercicios y aprovechar el servicios de [Play With Docker](https://labs.play-with-docker.com/), 
vamos a utilizar las distribuciones que corren sobre Docker. Por su sencillez y potencia vamos a utilizar `k3d`.

### Instalación de k3d

En una equipo con Docker ejecutar los siguientes comandos:

* `curl -s https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash`
* `k3d cluster create mycluster`
* `k3d --version`

### Instalación del kubectl

Kubectl es una interfaz de línea de comandos para ejecutar comandos sobre despliegues clusterizados de Kubernetes. Esta interfaz es la manera estándar de comunicación con el clúster 
ya que permite realizar todo tipo de operaciones sobre el mismo. Para instalarlo:

* `curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"`
* `sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl`
* `kubectl version --client --output=yaml `

### Comando básicos con kubectl

- `get`: Para obtener recursos. Muy utilizado.
- `edit`: Para editar los recursos
- `delete`: Para borrar recursos
- `apply`: Aplicar un manifiesto de Kubernetes o configuraciones.
- `exec`: Para ejecutar un comando dentro de un contenedor (Igual que en docker)
- `logs`: Para ver los logs
- `cp`: Para copiar archivos de una máquina al contenedor o viceversa
- `port-forward`: Para reenviar puertos para acceder a un contenedor

- `cordon`, `uncordon`, `drain`: Muy útiles a la hora de manejar nodos.
    - `cordon`: Hacer que el nodo no reciba más contenedores
    - `uncordon`: Hacer que el nodo pueda recibir más contenedores
    - `drain`: Sacar todos los contenedores del nodo, para moverlos a otro sitio.

## Nuestro Primer Pods

Vamos a lanzar nuestro primer pods, utilizando el siguiente comando:

* `kubectl run hola-mundo --image=vacax/javalin-hola-mundo --port=7000` - La imagen está disponible en el repositorio.

Si visualizamos los pods que están en ejecución:

* `kubectl get pods`

La información del Pods creado:

* `kubectl describe pod/hola-mundo`

Si queremos acceder al servicio del pods:

* `kubectl port-forward --address 0.0.0.0 pod/hola-mundo 8000:7000` - Desde el localhost o la IP del host mediante el puerto 8000 accedemos al servicio del pods.

Podemos borrar nuestro pods:

* `kubectl delete pod/hola-mundo`

## Manifiestos de Kubernetes

Un archivo de manifiesto de Kubernetes incluye instrucciones en un archivo yaml o json que especifican cómo desplegar una aplicación al nodo o nodos en un cluster de Kubernetes.

`namespace`: División lógica del cluster de kubernetes. Permite separar la carga en el cluster. Se suelen utilizar para dividir el tráfico o los recursos (por ejemplo, a partir del uso de políticas).

Muestra los namespace por defecto de cualquier cluster:

`kubectl get ns`

Aquellos que comiencen por `kube-*` están reservados para las herramientas de Kubernetes

Para mostrar los pods:

`kubectl -n kube-system get pods` 
- `-n` Para indicar el `namespace`

- Algunos pods tienen un hash en el nombre para diferenciarse unos de otros al haber varios iguales.
- En `READY` se muestra el número de contenedores en ejecución respecto del total.

Para ver más información de los pods:

`kubectl -n kube-system get pods -o wide`

- Ver en qué nodo se ejecutan los pods o la IP.

Borrar un pod

`kubectl -n kube-system delete pod nombre_del_pod`

Al hacer esto nuevamente se crea uno nuevo con otro hash.

Para ver el estado y eventos de un pod:

`kubectl describe pod nombre_del_pod`

### Manifiesto Pod Simple

Secciones:
- `apiVersion`: La versión de la API de este recurso de Kubernetes. Puede cambiar dependiendo del tipo de recurso (`kind`). Hay que utilizar la documentación de Kubernetes para ver qué `apiVersion` usa el tipo `pod`.

- `metadata`: Añadir datos como nombre, etiquetas. El `name` es necesario sí o sí.

- `spec`: Se compone de varias secciones:
  - `containers`: Declarar los contenedores dentro del pod. Estos pods compartirán el `namespace` de red, es decir, todos los contenedores que se ejecuten bajo el mismo pod tendrán la misma IP. Se declara el `name` y la `image` del contenedor.

Archivo de ejemplo: `hola-mundo-pods.yml`.

### Aplicar manifiesto de Kubernetes

`kubectl apply -f pods/hola-mundo-pods.yml`

Si no se aplica un `namespace` se aplicará el manifiesto en el `default`.

`kubectl get pods`

o

`kubectl -n default get pods`


### Ejecutar comando en pod

`kubectl exec -it hola-mundo -- sh`

Ejecutar `sh` dentro del pod llamado `hola-mundo`.

*No hace falta hacer ssh al nodo. Sólo se utiliza la API de Kubernetes.* 

### Opciones más comunes dentro del manifiesto de un pod

- `env`: Variables de entorno. Igual que en Docker. Dentro de `env` se indica el nombre de la variable y el valor. El valor se puede heredar a partir de información contenida en Kubernetes ([`downward API`](https://kubernetes.io/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/)), por ejemplo: `status.hosIP`.

- `resources`: Forma de asignar recursos a los contenedores. Es posible asignar diferentes recursos a distintos contenedores. Existen dos formas de asignar recursos: `requests` y `limits`.

  - `requests`: Son los recursos que se van a garantizar y siempre va a tener disponible.

  - `limits`: Es el límite que el contenedor puede llegar a utilizar. Cuando se sobrepasa el límite, el Kernel de Linux mata el proceso por usar más RAM de la permitida. En el caso de la CPU, el Kernel de Linux utiliza el servicio CPU throttling para ahogar al contenedor hasta que baje su consumo.

  Se definen `memory` y `cpu` comúnmente:
  - `memory`: Memoria RAM (ejemplo: `64Mi`)
  - `cpu`: Medidos en unidades de CPU. Una CPU en Kubernetes equivale a 1 vCPU/Core, para asignar recursos de CPU se utiliza `100m` o `0.1` para indicar que se usan `100milicores` o `100miliCPUs` (ejemplo: `200m`). No es posible superar las capacidades del pod. Si tenemos 1 core, no podemos meter más de 5 contenedores que utilicen `200m`.

  NOTA: Es por contenedor y no por pod
  NOTA2: Ya existen pods en ejecución de Kubernetes, hay que tener en cuenta que usan recursos.

- `readinessProbe`: Para indicar a Kubernetes que el contenedor está listo para recibir tráfico. Esto es útil para aplicaciones que tarden en iniciar, así Kubernetes no mata el proceso. Kubernetes esperará un status code 200 (OK).

- `livenessProbe`: Para indicar a Kubernetes que el contenedor está activo/vivo y no se desea que se apague. Kubernetes comprobará que el puerto esté abierto

- `ports`: Para indicar el puerto del contenedor.

Archivo de ejemplo: `pods/pod-con-liveness.yml`

`kubectl apply -f pods/pod-con-liveness.yml`

Para ver el estado del pod:

`kubectl get pod pod-con-liveness`

Para mostrar información adicional del pod

`kubectl get pod pod-con-liveness -o wide`

Para mostrar toda la información en `yaml` del pod:

`kubectl get pod pod-con-liveness -o yaml`

Borrar el pod:

`kubectl delete pod pod-con-liveness`

### Desplegar varios pods mediante `Deployment`

En este caso se utiliza un `Deployment`, que es un template para crear/desplegar pods. La estructura es similar a un pod, cambia el `kind` y el `apiVersion`.

También se ve un `spec` dentro de otro `spec` que sirve como template para los pods.

`replicas`: Cantidad de pods en el deployment. Se le indica a Kubernetes cuántas réplicas de un pod se desean tener. Se definen cuántas réplicas (pods) de la aplicación se desean ejecutar en la definición del `deployment` y Kubernetes hará que esas réplicas de la aplicación se distribuyan entre los nodos. Si se establecen `5` réplicas en 3 nodos, algunos nodos tendrán más de una réplica de la aplicación en ejecución.

Archivo: `deployments/deployments-basico.yml`

Despliegue de los pods mediante el deployment:

`kubectl apply -f deployments/deployment-basico.yml`

Se crean 4 pods con un hash para diferenciarse. Durante el proceso de creación si se consulta el estado de los pods se puede ver:

- `0/1 READY` lo cual indica `readinessProbe` a Kubernetes que no se le debe mandar tráfico.

Una vez ambos pods estén en estado `RUNNING` se puede intentar eliminar uno de ellos para ver si Kubernetes levanta de nuevo al haberle indicado que siempre se desean 4 réplicas.

Para hacer una consulta de los deployments:

`kubectl get deployments -o wide`

Se pueden eliminar los deployments al igual que los pods:

`kubectl delete deployment deployment-basico`

Podemos escalar o disminuir los pods que intervienen en un deployment con el siguiente comando:

`kubectl scale deployments deployment-basico --replicas=6`

Notar que la cantidad de pods se incrementan, si ponemos un valor menor aumenta.

### Networking Kubernetes

[Cluster Networking](https://kubernetes.io/docs/concepts/cluster-administration/networking/)

Comentarios:

- Cada pod tiene su propia IP, pero cada contenedor dentro de un pod compartirá la IP del pod.
- Si hay un pod con 3 contenedores, todos tendrán la misma IP que es la del pod. Es importante vigilar los puertos de los contenedores.

En Kubernetes existen distintos tipos de servicios, que son el mecanismo que permite comunicar pods. Existen tres servicios:

- `ClusterIP`: IP fija dentro del cluster, sirve como un pequeño load balancer entre todos los pods que se le asigna al servicio.
- `NodePort`: Crea un puerto en cada nodo que va a recibir el tráfico y lo va a mandar a los pods específicos. Por ejemplo, si quiero acceder al pod del servicio A y le pego al worker a un puerto en específico, ese tráfico va a encontrar el nodo que está corriendo ese pod y encontrará el tráfico
- `LoadBalancer`: Enfocado a la nube, crear balanceadores de carga en nuestro proveedor y redireccionará el tráfico entre los pods.

### Servicios

* Los servicios permiten que nosotros podamos enviar peticiones a las aplicaciones dentro del cluster.
* Las IP de los pods no son estables, es decir, que son manejadas por kubernetes y es quien decide su asignación, nosotros no podemos apuntar a esas iP que no son accesible dentro fuera del cluster.
* Los servicios nos permiten abstraer el conjunto de pods como si fuera una misma dirección.
* Cuando se crea un servicio el endpoint controller, se crea un endpoint que contendrá la IP de los pods, los pods son elegidos mediante etiquetas.
* Los servicios mantiene un IP y nombre estable. 
* Los servicios utilizan los label que hemos utilizando en los selectores y se encargaran de reenviar las peticiones.
* Tenemos el tipo de conexión que podemos trabajar, el ClusterIP es el tipo de IP accesible únicamente por los IP, se utiliza para comunicación interna entre ellos. 
* El Nodeport expondrá el servicio a un puerto accesible desde la IP del node más puerto del cluster, el puerto utilizando será el mismo para todos los nodos.
* Loadbalancer expondrá una IP y puerto que aplicará un balanceador de carga, disponible de forma sencilla en los proveedores de la nube.


Probando el servicio tipo ClusterIP:

* `kubectl apply -f servicios/pod-para-servicios.yml`
* `kubectl apply -f servicios/servicio-basico.yml`

Validamos que estén arriba los pods y servicios.

`kubectl get all`

Creamos una imagen y hacemos la llamada mediante el nombre del pod:

`kubectl run --rm -i --tty mi-cliente-app --image=alpine --restart=Never -- sh`

Una vez dentro del contenedor de pods:

`wget pod-para-servicio:7000 -qSO-` - Notar que estamos accediendo por el nombre del pod.

Vamos a probar el servicio utilizando NodePort, de esa forma con la IP del nodo y puerto 
se estará accediendo a los pods que responden por ese selector. 

Habilitando el puerto de k3d: `k3d cluster create mycluster -p "8082:30080@agent:0" --agents 2`

Visualizando los nodos - `kubectl get nodes -o wide`

Aplicando el servicio:

`kubectl apply -f servicios/deployment-basico-servicio-nodeport.yml`