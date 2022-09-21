package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Product Category model.
 */
@Schema(description = "Product category model")
public class CategoryDto {

   /**
    * Category ID.
    */
   @Schema(description = "Category ID", example = "1")
   private Long id;

   /**
    * Category name.
    */
   @Schema(description = "Category name", required = true, example = "Technic")
   private String name;

   public CategoryDto() {
   }

   public CategoryDto(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}
