package live.destroyed.husna.Discord;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.bukkit.Bukkit;

public class AlertsDiscord {
   private final String webhookUrl;

   public AlertsDiscord(String webhookUrl) {
      this.webhookUrl = webhookUrl;
   }

   public void sendAlert(String message) {
      if (this.webhookUrl != null && !this.webhookUrl.isEmpty()) {
         try {
            URL url = new URL(this.webhookUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String var10000 = this.escapeSpecialCharacters(message);
            String jsonPayload = "{\"content\": \"" + var10000 + "\"}";
            OutputStream os = connection.getOutputStream();

            try {
               byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
               os.write(input);
            } catch (Throwable var9) {
               if (os != null) {
                  try {
                     os.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (os != null) {
               os.close();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 204) {
               Bukkit.getLogger().info("[Husna] Mensaje enviado a Discord con éxito.");
            } else {
               Bukkit.getLogger().warning("[Husna] Error al enviar mensaje a Discord. Código de respuesta: " + responseCode);
            }
         } catch (Exception var10) {
            Bukkit.getLogger().severe("[Husna] Error al enviar mensaje al webhook de Discord: " + var10.getMessage());
         }

      } else {
         Bukkit.getLogger().warning("[Husna] No se ha configurado la URL del webhook de Discord.");
      }
   }

   private String escapeSpecialCharacters(String message) {
      return message.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
   }
}
