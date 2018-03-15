package dropwizard.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import dropwizard.View;

import java.security.Principal;

public class Product implements Principal {
    @NotEmpty
    @Length(min = 1, max = 8)
    @JsonView(View.Private.class)
    private int id;

    @NotEmpty
    @Length(min = 3, max = 50)
    @JsonView(View.Public.class)
    private String omschrijving;

    @NotEmpty
    @Length(min = 8, max = 255)
    @JsonView(View.Public.class)
    private String fabrikant;

    @NotEmpty
    @Length(min = 1, max = 255)
    @JsonView(View.Public.class)
    private int gecheckt;

    @NotEmpty
    @Length(min = 1, max = 24)
    @JsonView(View.Public.class)
    private double prijs;

    @NotEmpty
    @Length(min = 1, max = 255)
    @JsonView(View.Public.class)
    private int product_gebruiker_id;

    @NotEmpty
    @Length(min = 6, max = 128)
    @JsonView(View.Public.class)
    private String imgURL;



    public Product() {

    }
    //public id?: number,
//public omschrijving?: string,
//public fabrikant?: string,
//public gecheckt?: string,
//public prijs?: string,
//public product_gebruiker_id?: string,
//public imgURL: string)
    public Product(int id, String omschrijving, String fabrikant, int gecheckt, double prijs, int product_gebruiker_id, String imgURL) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.fabrikant = fabrikant;
        this.gecheckt = gecheckt;
        this.prijs = prijs;
        this.product_gebruiker_id = product_gebruiker_id;
        this.imgURL = imgURL;
    }

    public Product(String omschrijving, String fabrikant, int gecheckt, double prijs, int product_gebruiker_id, String imgURL) {
        this.omschrijving = omschrijving;
        this.fabrikant = fabrikant;
        this.gecheckt = gecheckt;
        this.prijs = prijs;
        this.product_gebruiker_id = product_gebruiker_id;
        this.imgURL = imgURL;
    }

    @Override
    public String getName() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getFabrikant() {
        return fabrikant;
    }

    public void setFabrikant(String fabrikant) {
        this.fabrikant = fabrikant;
    }

    public int getGecheckt() {
        return gecheckt;
    }

    public void setGecheckt(int gecheckt) {
        this.gecheckt = gecheckt;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public int getProduct_gebruiker_id() {
        return product_gebruiker_id;
    }

    public void setProduct_gebruiker_id(int product_gebruiker_id) {
        this.product_gebruiker_id = product_gebruiker_id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}