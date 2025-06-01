package live.destroyed.husna.model;

import java.time.LocalDateTime;

public class AlertDetail {
   private final LocalDateTime time;
   private final String minedBlock;
   private final int quantity;

   public AlertDetail(LocalDateTime time, String minedBlock, int quantity) {
      this.time = time;
      this.minedBlock = minedBlock;
      this.quantity = quantity;
   }

   public LocalDateTime getTime() {
      return this.time;
   }

   public String getMinedBlock() {
      return this.minedBlock;
   }

   public int getQuantity() {
      return this.quantity;
   }
}
