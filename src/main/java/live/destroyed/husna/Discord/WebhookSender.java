package live.destroyed.husna.Discord;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.bukkit.Bukkit;
import live.destroyed.husna.Husna;

public class WebhookSender {
   private final String webhookUrl;
   private final Husna plugin;

   public WebhookSender(String webhookUrl, Husna plugin) {
      this.webhookUrl = webhookUrl;
      this.plugin = plugin;
   }

   public void sendEmbed(String playerName, int count, String blockType, int seconds) {
      if (this.plugin.getMainConfigManager().getWebenbale()) {
         if (this.webhookUrl != null && !this.webhookUrl.isEmpty()) {
            try {
               URL url = new URL(this.webhookUrl);
               HttpURLConnection connection = (HttpURLConnection)url.openConnection();
               connection.setRequestMethod("POST");
               connection.setRequestProperty("Content-Type", "application/json");
               connection.setRequestProperty("User-Agent", "Mozilla/5.0");
               connection.setDoOutput(true);
               JsonObject embed = new JsonObject();
               String title = this.plugin.getConfig().getString("Discord.embed.title");
               title = this.replacePlaceholders(title, playerName, count, blockType, seconds);
               embed.addProperty("title", title);
               List<String> descriptionLines = this.plugin.getConfig().getStringList("Discord.embed.description");
               StringBuilder description = new StringBuilder();

               for(int i = 0; i < descriptionLines.size(); ++i) {
                  description.append(this.replacePlaceholders((String)descriptionLines.get(i), playerName, count, blockType, seconds));
                  if (i < descriptionLines.size() - 1) {
                     description.append("\n");
                  }
               }

               embed.addProperty("description", description.toString());
               String colorStr = this.plugin.getConfig().getString("Discord.embed.color", "15158332");

               int color;
               try {
                  if (colorStr.startsWith("#")) {
                     color = Integer.parseInt(colorStr.substring(1), 16);
                  } else {
                     color = Integer.parseInt(colorStr);
                  }
               } catch (NumberFormatException var22) {
                  color = 15158332;
                  Bukkit.getLogger().warning("[Husna] Color inválido en config. Usando color por defecto.");
               }

               embed.addProperty("color", color);
               JsonArray embedsArray = new JsonArray();
               embedsArray.add(embed);
               JsonObject json = new JsonObject();
               json.add("embeds", embedsArray);
               String jsonString = json.toString();
               OutputStream os = connection.getOutputStream();

               try {
                  byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                  os.write(input);
                  os.flush();
               } catch (Throwable var23) {
                  if (os != null) {
                     try {
                        os.close();
                     } catch (Throwable var21) {
                        var23.addSuppressed(var21);
                     }
                  }

                  throw var23;
               }

               if (os != null) {
                  os.close();
               }

               int responseCode = connection.getResponseCode();
               if (responseCode == 204 || responseCode == 200) {
                  return;
               }

               BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));

               try {
                  StringBuilder response = new StringBuilder();

                  while(true) {
                     String responseLine;
                     if ((responseLine = br.readLine()) == null) {
                        Bukkit.getLogger().warning("[Husna] Error al enviar embed a Discord. Código: " + responseCode);
                        Bukkit.getLogger().warning("[Husna] Respuesta de error: " + response.toString());
                        break;
                     }

                     response.append(responseLine.trim());
                  }
               } catch (Throwable var24) {
                  try {
                     br.close();
                  } catch (Throwable var20) {
                     var24.addSuppressed(var20);
                  }

                  throw var24;
               }

               br.close();
               connection.disconnect();
            } catch (Exception var25) {
               Bukkit.getLogger().severe("[Husna] Error al enviar embed al webhook de Discord: " + var25.getMessage());
               var25.printStackTrace();
            }

         } else {
            Bukkit.getLogger().warning("[Husna] No se ha configurado la URL del webhook de Discord.");
         }
      }
   }

   private String replacePlaceholders(String text, String playerName, int count, String blockType, int seconds) {
      return text.replace("%p", playerName).replace("%c", String.valueOf(count)).replace("%b", blockType).replace("%s", String.valueOf(seconds));
   }
}
