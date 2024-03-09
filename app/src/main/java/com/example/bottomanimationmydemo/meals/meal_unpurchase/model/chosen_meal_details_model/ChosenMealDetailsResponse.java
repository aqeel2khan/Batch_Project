
package com.example.bottomanimationmydemo.meals.meal_unpurchase.model.chosen_meal_details_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ChosenMealDetailsResponse {

    @Expose
    private Data data;
    @Expose
    private Error error;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public class InternalData {


        @SerializedName("avg_preparation_time")
        private String avgPreparationTime;
        @SerializedName("category_id")
        private Long categoryId;
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        private String description;
        @SerializedName("description_ar")
        private String descriptionAr;
        @Expose
        private Long id;
        @Expose
        private String image;
        @Expose
        private String ingredients;
        @SerializedName("is_vegetarian")
        private Long isVegetarian;
        @Expose
        private String name;
        @SerializedName("name_ar")
        private String nameAr;
        @SerializedName("nutrition_details")
        private List<NutritionDetail> nutritionDetails;
        @SerializedName("order_in_menu")
        private Long orderInMenu;
        @Expose
        private String price;
        @SerializedName("restaurant_id")
        private Long restaurantId;
        @Expose
        private Long status;
        @SerializedName("updated_at")
        private Object updatedAt;

        public String getAvgPreparationTime() {
            return avgPreparationTime;
        }

        public void setAvgPreparationTime(String avgPreparationTime) {
            this.avgPreparationTime = avgPreparationTime;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescriptionAr() {
            return descriptionAr;
        }

        public void setDescriptionAr(String descriptionAr) {
            this.descriptionAr = descriptionAr;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIngredients() {
            return ingredients;
        }

        public void setIngredients(String ingredients) {
            this.ingredients = ingredients;
        }

        public Long getIsVegetarian() {
            return isVegetarian;
        }

        public void setIsVegetarian(Long isVegetarian) {
            this.isVegetarian = isVegetarian;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public List<NutritionDetail> getNutritionDetails() {
            return nutritionDetails;
        }

        public void setNutritionDetails(List<NutritionDetail> nutritionDetails) {
            this.nutritionDetails = nutritionDetails;
        }

        public Long getOrderInMenu() {
            return orderInMenu;
        }

        public void setOrderInMenu(Long orderInMenu) {
            this.orderInMenu = orderInMenu;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Long getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
        }

        public Long getStatus() {
            return status;
        }

        public void setStatus(Long status) {
            this.status = status;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Data {
        public InternalData getData() {
            return data;
        }

        public void setData(InternalData data) {
            this.data = data;
        }

        private InternalData data;
        private String status;

    }

    public class NutritionDetail {

        @SerializedName("nutrient_id")
        private Long nutrientId;
        @SerializedName("nutrient_name")
        private String nutrientName;
        @SerializedName("nutrient_name_ar")
        private String nutrientNameAr;
        @Expose
        private String value;

        public Long getNutrientId() {
            return nutrientId;
        }

        public void setNutrientId(Long nutrientId) {
            this.nutrientId = nutrientId;
        }

        public String getNutrientName() {
            return nutrientName;
        }

        public void setNutrientName(String nutrientName) {
            this.nutrientName = nutrientName;
        }

        public String getNutrientNameAr() {
            return nutrientNameAr;
        }

        public void setNutrientNameAr(String nutrientNameAr) {
            this.nutrientNameAr = nutrientNameAr;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
    public class Error {


    }

}
