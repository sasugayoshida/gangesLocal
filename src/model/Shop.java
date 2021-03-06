package model;

import org.json.simple.JSONObject;

import util.JsonApi;
import util.Util;

public class Shop implements Jsonable {
	public String shop_id;
	public String user_id;
	public String name;
	public String made_date;
	public String exterior_image_path;
	public String map_id;
	public int point_x;
	public int point_y;
	public int is_alive;
	public String interior_image_path;
	public String interior_draw_data;
	public String interior_object_data;
	public String interior_collision_data;


	public Shop(){}
	public Shop(String shop_id, String user_id, String name, String made_date, String exterior_image_path,
      String map_id, int point_x, int point_y, int is_alive, String interior_image_path,
      String interior_draw_data, String interior_object_data, String interior_collision_data) {

	  this.shop_id= shop_id;
	  this.user_id = user_id;
	  this.name = name;
	  this.made_date = made_date;
	  this.exterior_image_path = exterior_image_path;
	  this.map_id = map_id;
	  this.point_x = point_x;
	  this.point_y = point_y;
	  this.is_alive = is_alive;
	  this.interior_collision_data = interior_collision_data;
	  this.interior_draw_data = interior_draw_data;
	  this.interior_object_data= interior_object_data;
	  this.interior_image_path = interior_image_path;

  }
	public String toString(){
	  return "shop_id : " + shop_id+
	         "point_x : " + point_x+
	         "point_y : " + point_y;
	}

	public JSONObject toJson(){
		JSONObject obj = new JSONObject();
		obj.put("point_x", new Integer(this.point_x));
		obj.put("point_y", new Integer(this.point_y));
		obj.put("shop_id", this.shop_id);
		obj.put("user_id",this.user_id);
		obj.put("interior_image_path",this.interior_image_path);
		obj.put("interior_draw_data",JsonApi.int2DArrayToJSONArray(Util.mapStringToInt2DArray(this.interior_draw_data)));
		obj.put("interior_object_data",JsonApi.int2DArrayToJSONArray(Util.mapStringToInt2DArray(this.interior_object_data)));
		obj.put("interior_collision_data",JsonApi.int2DArrayToJSONArray(Util.mapStringToInt2DArray(this.interior_collision_data)));
		obj.put("exterior_image_path", this.exterior_image_path);
		return obj;
  }



  public String getShop_id() {
    return shop_id;
  }
  public void setShop_id(String shop_id) {
    this.shop_id = shop_id;
  }
  public String getUser_id() {
    return user_id;
  }
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getMade_date() {
    return made_date;
  }
  public void setMade_date(String made_date) {
    this.made_date = made_date;
  }
  public String getExterior_image_path() {
    return exterior_image_path;
  }
  public void setExterior_image_path(String exterior_image_path) {
    this.exterior_image_path = exterior_image_path;
  }
  public String getMap_id() {
    return map_id;
  }
  public void setMap_id(String map_id) {
    this.map_id = map_id;
  }
  public int getPoint_x() {
    return point_x;
  }
  public void setPoint_x(int point_x) {
    this.point_x = point_x;
  }
  public int getPoint_y() {
    return point_y;
  }
  public void setPoint_y(int point_y) {
    this.point_y = point_y;
  }
  public int getIs_alive() {
    return is_alive;
  }
  public void setIs_alive(int is_alive) {
    this.is_alive = is_alive;
  }
  public String getInterior_image_path() {
    return interior_image_path;
  }
  public void setInterior_image_path(String interior_image_path) {
    this.interior_image_path = interior_image_path;
  }
  public String getInterior_draw_data() {
    return interior_draw_data;
  }
  public void setInterior_draw_data(String interior_draw_data) {
    this.interior_draw_data = interior_draw_data;
  }
  public String getInterior_object_data() {
    return interior_object_data;
  }
  public void setInterior_object_data(String interior_object_data) {
    this.interior_object_data = interior_object_data;
  }
  public String getInterior_collision_data() {
    return interior_collision_data;
  }
  public void setInterior_collision_data(String interior_collision_data) {
    this.interior_collision_data = interior_collision_data;
  }




}
