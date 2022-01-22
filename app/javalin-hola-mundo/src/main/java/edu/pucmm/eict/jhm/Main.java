package edu.pucmm.eict.jhm;

import io.javalin.Javalin;
import io.javalin.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static j2html.TagCreator.*;

public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        //
        int llamadasTotales = 50;
        var llamada = new Object() {
            int cantidadLlamadas = 0;
        };

        //Llamada simple vía Javalin
        app.get("/", ctx -> {
            //Variable de ambiente enviando por Kubernates para los nombres.
            String nombrePod = System.getenv("POD_NAME");
            ctx.result("Hola Mundo en Javalin - Pod: "+nombrePod);
        });

        /**
         * Endpoint para simular la salud de la aplicación.
         * Dependiendo el check de validación retornará error 500
         * para obligar a kubenerte al reinicio.
         */
        app.get("/health", ctx -> {
            logger.info("Petición #{} de #{}", (++llamada.cantidadLlamadas), llamadasTotales );
            if(llamada.cantidadLlamadas>=llamadasTotales){
                ctx.status(500);
                logger.info("Error en el chequeo de la salud del contenedor.");
            }
            ctx.result(String.format("Proceso health %d de %d", llamada.cantidadLlamadas, llamadasTotales));
        });

        /**
         * Endpoint para visualizar las variables de ambientes
         */
        app.get("/ambiente", ctx -> {
            //Las variables de ambientes.
            String variable1 = System.getenv("VARIABLE1");
            String variable2 = System.getenv("VARIABLE2");
            //uso de librería J2html, https://j2html.com/
            //para el render del mensaje.
            ctx.contentType(ContentType.TEXT_HTML);
            ctx.result(html(
                    body(
                           h1("Valores desde las Variables de Ambiente"),
                           ul(
                                   li("Variable #1 -> "+variable1),
                                   li("Variable #2 -> "+variable2)
                           )
                    )
            ).render());
        });

    }
}
