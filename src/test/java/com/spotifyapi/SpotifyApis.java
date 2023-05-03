package com.spotifyapi;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SpotifyApis {
    String userId;
    String playlistId;
    String type="user";
    String ids="74ASZWbe4lXaubB36ztrGX,08td7MxkoHQkXnWAYD8d6Q";
    static String authToken="Bearer BQCel5VwSA8Zf_sfSBdlXXBMKNBftvIXiV_D4ZcTgHrTmWfIcKERqIlJAmhCQ25VF1VhsqSRVjoGAD8JUtBrRWlYCTYtbhEeTpsy2T-DqdRntq3SM38BAMPG0SMjOq9tD7C2MZ-xahBQDM_g0gWUlemR_ulGh9PzH84FmEPzDcZiz4aCY6eVjMWTMPggMpOgA6uF3VxLJ1lYmWzN-2sWCznJEsLDfuvu5AzBcxMbV0IxLahS-XeXJ51NwV3_3G6OB9I22C4k59wrK1tzg1XzKlv9CGJqtIEk-WLHYsgjoB1HECLNOSMx4WHQP52qbI91VQWqC8S6WVXKaw";
    @Test(priority = 0)
    public void getCurrentUserProfile_ReturnOk_Success(){
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/me");
        response.prettyPrint();
        userId=response.path("id");
        System.out.println("userId:"+userId);
        response.then().assertThat().statusCode(200);


    }
    @Test(priority = 1)
    public void getUsersProfile_ReturnOk_Success(){
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .get("https://api.spotify.com/v1/users/"+userId);
        response.prettyPrint();
        userId=response.path("id");
        System.out.println("userId:"+userId);
        type=response.path("type");
        System.out.println("type:"+type);
        response.then().assertThat().statusCode(200);
    }
    @Test(priority = 2)
        public void createPlaylist_ReturnOk_Success()
        {
            Response response= given().accept("application/json")
                    .contentType("application/json")
                    .header("Authorization",authToken)
                    .body(" {\n" +
                            "    \"name\": \"My Playlist13\",\n" +
                            "    \"description\": \"New playlist songs\",\n" +
                            "    \"public\": false\n" +
                            "}")
                    .when()
                    .post("https://api.spotify.com/v1/users/"+userId+"/playlists");
            response.prettyPrint();
            playlistId=response.path("id");
            System.out.println("playlistId:"+playlistId);
            response.then().assertThat().statusCode(201);
        }
    @Test(priority =3)
    public void getUsersTopItems_ReturnOk_Success(){
        type="artists";
        Response response= given().accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/me/top/"+type);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);


    }

    @Test(priority =4)
    public void followPlaylist_ReturnOk_Success(){
        Response response= given().accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization",authToken)
                .body("{\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistId+"/followers");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);


    }
    @Test(priority =5)
    public void unFollowPlaylist_ReturnOk_Success(){
        Response response= given().accept("application/json")
                .contentType(ContentType.JSON)
                .header("Authorization",authToken)
                .when()
                .delete("https://api.spotify.com/v1/playlists/"+playlistId+"/followers");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);


    }
    @Test(priority = 6)
    public void checkIfUserFollowsArtistsOrUsers()
    {
        Response response= given().accept("application/json")
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/me/following/contains");
        response.prettyPrint();
        //response.then().assertThat().statusCode(200);
    }

    @Test(priority = 7)
    public void getUsersPlaylist_ReturnOk_Success(){
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/playlists");
        response.prettyPrint();
        //userId=response.path("id");
        //System.out.println("userId:"+userId);
        response.then().assertThat().statusCode(200);
    }


    @Test(priority = 8)
    public void addItemsToPlaylist_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .body("{\"uris\": \n" +
                        "[\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\",\"spotify:track:1301WleyT98MSxVHPZCA6M\", \"spotify:episode:512ojhOuo1ktJprKbVcKyQ\"]\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
    }
    @Test(priority = 9)
    public void updatePlaylistItems_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 3,\n" +
                        "    \"range_length\": 2\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test(priority = 10)
    public void GetPlaylistCoverImage_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .contentType("application/json")
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistId+"/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test(priority = 11)
    public void removePlaylistItems_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .header("Authorization",authToken)
                .body("{\n" +
                        "    \"name\": \"Updated list soujanya12\",\n" +
                        "    \"description\": \"melody songs\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
       // response.then().statusCode(200);
    }
    @Test(priority = 12)
    public void getFeaturedPlaylists_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .header("Authorization",authToken)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 13)
    public void getCategorysPlaylists_ReturnOk_Success()
    {
        Response response= given().accept("application/json")
                .header("Authorization",authToken)
                .pathParams("category_id","dinner")
                .when()
                .get("https://api.spotify.com/v1/browse/categories/{category_id}/playlists");
        response.prettyPrint();
        response.then().statusCode(200);
    }


}

