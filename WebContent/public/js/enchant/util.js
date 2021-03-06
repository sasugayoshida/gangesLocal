var game;
var newChara;



var createPlayer = function(){
	var DIR_LEFT  = 0;
	var DIR_RIGHT = 1;
	var DIR_UP    = 2;
	var DIR_DOWN  = 3;
	var player = new Sprite(32,32)
    player.image = game.assets['/ganges/public/images/chara0.png'];
    player.x = 160;
    player.y = 160;
    player.dir   = DIR_DOWN;
    player.anim  = [
         9, 10, 11, 10, //左
        18, 19, 20, 19, //右
        27, 28, 29, 28, //上
         0,  1,  2,  1];//下

    //プレイヤーの定期処理
  player.tick = 0;
    player.addEventListener(Event.ENTER_FRAME, function() {
        //上へ移動
        if (game.input.up) {
            player.dir = DIR_UP;
            player.y -= 4;
            if (global.currentMap.hitTest(player.x + 16, player.y + 32)) player.y += 4;
        }
        //下へ移動
        else if (game.input.down) {
            player.dir = DIR_DOWN;
            player.y += 4;
            if (global.currentMap.hitTest(player.x + 16, player.y + 32)) player.y -= 4;
        }
        //左へ移動
        else if (game.input.left) {
            player.dir = DIR_LEFT;
            player.x -= 4;
            if (global.currentMap.hitTest(player.x + 16, player.y + 32)) player.x += 4;
        }
        //右へ移動
        else if (game.input.right) {
            player.dir = DIR_RIGHT;
            player.x += 4;
            if (global.currentMap.hitTest(player.x + 16, player.y + 32)) player.x -= 4;
        }

        //フレームの指定
        player.tick++;
        if (!game.input.up && !game.input.down &&
            !game.input.left && !game.input.right) player.tick = 1;//静止
        player.frame = player.anim[player.dir * 4 + (player.tick % 4)];

    });
    return player;
};

var createMap = function(x,y){
	var map = new ExMap(16,16);
	var path = global.server.url+'EnchantApi?action=getMap&x='+x+'&y='+y;
	$.getJSON(path,function(json){
		map.x = json.point_x;
		map.y = json.point_y;
		map.image = game.assets['/ganges' +json.image_path];// /public/images/map1.png が返ってくる
		map.loadData(json.drawing_data,json.object_data);
		map.collisionData = json.collision_data;
		global.setShopList(x,y,JSON.parse(JSON.stringify(json.shopList)));
		createShops(x,y,map);
	});
	return map;
};

var createShops = function(x,y,map){//createMapから呼ぶこと
global.scene[x+":"+y].scene.addChild(map);
	global.scene[x+":"+y].scene.addChild(global.chara);
	global.scene[x+":"+y].scene.addChild(global.label);
	global.scene[x+":"+y].scene.addChild(global.mapChangeManager);
	var shop_exterior_frame = 22;//shopのframeに使用 本来はサーバから取得。
	var array = global.getShopList(x,y);
	for(var i = 0;i < array.length;i++){
		var shop = new Sprite(32,32);
		shop.image = game.assets['/ganges'+array[i].exterior_image_path];
		shop.frame = shop_exterior_frame;
		shop.x = array[i].point_x;
		shop.y = array[i].point_y;
		shop.shop_id = array[i].shop_id;
		shop.user_id = array[i].user_id;
		shop.interior_image_path = array[i].interior_image_path;

		shop.on('enterframe',function(){
			if(global.chara.intersect(this)){
				goInShop(x,y,this.shop_id);
				global.chara.next = {};
				global.chara.next.x = this.x + 50;
				global.chara.next.y = this.y;
				//this.removeEventListener('enterframe',arguments.callee); //このイベントりむると2回目に入れない
			}
		});
		global.scene[x+":"+y].scene.addChild(shop);
	}
};

var changeMap = function(x,y){
	var WIDTH  = config.game_width;
	var HEIGHT = config.game_height;
	if(global.chara.x >= WIDTH){
		global.chara.x = 0;
		global.setScene((x+1),y,new Scene());
		global.currentMap = createMap((global.currentMap.x+1),global.currentMap.y);
		global.currentMap.x = x+1;
		global.currentMap.y = y;
	}
	if(global.chara.x <= -10){
		global.chara.x = WIDTH - 30;
		global.setScene((x-1),y,new Scene());
		global.currentMap = createMap((global.currentMap.x-1),global.currentMap.y);
		global.currentMap.x = x-1;
		global.currentMap.y = y;
	}
	//mapをさきに加える。 chara mapとすると上書きされてしまう

	global.mapChangeManager.addEventListener('enterframe', function(){
		if(global.chara.x > WIDTH || global.chara.x < -10 || global.chara.y > HEIGHT || global.chara.y < 0){//画面端に触れたら
			changeMap(global.currentMap.x,global.currentMap.y);
			//this.removeEventListener('enterframe',arguments.callee); 消しちゃいけないと思われる。
		}
	 });

	setTouchMoveEvent(global.scene[global.currentMap.x+":"+global.currentMap.y].scene);

	game.pushScene(global.scene[global.currentMap.x+":"+global.currentMap.y].scene);
};

var goInShop = function( currentMap_x, currentMap_y, shop_id){
	//現在地保存しておく
	global.previous.x = global.chara.x;
	global.previous.y = global.chara.y;
	var scene 				= new Scene();
	var shopInterior 	= createShopInterior(currentMap_x, currentMap_y, shop_id);

	global.shopChangeManager.on('enterframe',function(){
		if ( global.chara.y > config.game_height){
			game.popScene();
			var nextScene = global.scene[currentMap_x+":"+currentMap_y].scene;
			nextScene.addChild(global.chara);
			nextScene.addChild(global.label);
			global.chara.x = global.chara.next.x;
			global.chara.y = global.chara.next.y;
		}
	});

	scene.addChild(shopInterior);
	scene.addChild(global.chara);
	scene.addChild(global.label);
	scene.addChild(global.shopChangeManager);
	setTouchMoveEvent(scene);

	global.chara.x = 50;
	global.chara.y = config.game_height - 30;
	game.pushScene(scene);
};

var createShopInterior = function(currentMap_x, currentMap_y, shop_id){
	var shop  					= global.getShopObject(currentMap_x, currentMap_y, shop_id);
	var interiorMap		  = new ExMap(16,16);
	var tmp = '/ganges'+shop.interior_image_path;
	interiorMap.image 	= game.assets['/ganges' + shop.interior_image_path];
	interiorMap.loadData(shop.interior_draw_data, shop.interior_object_data);
	interiorMap.collisionData = shop.interior_collision_data;

	return interiorMap
}

var setTouchMoveEvent = function(scene){
	scene.on('touchstart',function(e){
		global.chara.x = e.x;
		global.chara.y = e.y;
	});
}


