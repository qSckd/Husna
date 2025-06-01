package live.destroyed.husna.model;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class ProfileUser implements PlayerProfile {
   public UUID getUniqueId() {
      return null;
   }

   public String getName() {
      return "";
   }

   public PlayerTextures getTextures() {
      return null;
   }

   public void setTextures(PlayerTextures playerTextures) {
   }

   public boolean isComplete() {
      return false;
   }

   public CompletableFuture<PlayerProfile> update() {
      return null;
   }

   public PlayerProfile clone() {
      return null;
   }

   public Map<String, Object> serialize() {
      return Map.of();
   }
}
