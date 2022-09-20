package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Product Category model.
 */
@Schema(description = "Модель категории товаров")
public class CategoryDto {

   /**
    * Category ID.
    */
   @Schema(description = "ID категории", example = "1")
   private Long id;

   /**
    * Category name.
    */
   @Schema(description = "Наименование категории", required = true, example = "Техника")
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
