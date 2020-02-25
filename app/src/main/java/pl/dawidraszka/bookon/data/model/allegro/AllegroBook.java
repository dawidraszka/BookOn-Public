package pl.dawidraszka.bookon.data.model.allegro;

import com.google.gson.JsonObject;

public class AllegroBook {

    private long id;
    private String title;
    private float price;
    private float deliveryPrice;
    private String imageUrl;

    public AllegroBook(JsonObject jsonObject) {
        id = jsonObject.get("id").getAsLong();
        title = jsonObject.get("name").getAsString();
        price = jsonObject.getAsJsonObject("sellingMode").getAsJsonObject("price").get("amount").getAsFloat();
        deliveryPrice = jsonObject.getAsJsonObject("delivery").getAsJsonObject("lowestPrice").get("amount").getAsFloat();
        if (jsonObject.getAsJsonArray("images").size() > 0) {
            imageUrl = jsonObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
