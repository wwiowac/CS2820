package production;

/**
 * Created by kyle on 11/10/16.
 */
public class InventoryList {

    /**
     * Taken from @Author - Prof. Herman's code
     * description: a list of potential items that can be found in the warehouse
     */
    static final InventoryItem[] catalog = {
            new InventoryItem(547840,"1-Cup Coffee and Espresso Maker"),
            new InventoryItem(150644,"11lb Kitchen Scale"),
            new InventoryItem(424962,"12V Power Adapter for Cars"),
            new InventoryItem(490499,"26-Function Bike Tool"),
            new InventoryItem(573447,"3-Step Stool"),
            new InventoryItem(533512,"32 GB SD Card"),
            new InventoryItem(439305,"34oz Insulated Themos"),
            new InventoryItem(106510,"4-Quart Tupperware"),
            new InventoryItem(628751,"4-Tier Cooling Rack"),
            new InventoryItem(379920,"40 Quart Stock Pot"),
            new InventoryItem(258065,"5-Bike Rack"),
            new InventoryItem(372758,"5-Port Networking Switch"),
            new InventoryItem(136217,"61oz Thermos"),
            new InventoryItem(898073,"A-Male to Mini-B USB Cable"),
            new InventoryItem(32794,"A-to-B USB Cable"),
            new InventoryItem(346139,"AA Battery Charger"),
            new InventoryItem(305186,"AA Rechargeable Batteries"),
            new InventoryItem(236580,"Adjustable Basketball Hoop"),
            new InventoryItem(707622,"Adjustable Dumbbells"),
            new InventoryItem(896039,"Adjustable Gym Bench"),
            new InventoryItem(973867,"Air Freshener"),
            new InventoryItem(501806,"Airtight Container"),
            new InventoryItem(691247,"Anti-Chafe Balm"),
            new InventoryItem(538672,"Apple Peeler Contraption"),
            new InventoryItem(319537,"Apron with Pockets"),
            new InventoryItem(267316,"Audio Cable"),
            new InventoryItem(338998,"Audio Contact Cleaner Spray"),
            new InventoryItem(52279,"Audio-Technica Headphones"),
            new InventoryItem(316191,"Auto Code Reader"),
            new InventoryItem(152928,"Backpack"),
            new InventoryItem(324674,"Backpack Full of Water"),
            new InventoryItem(460868,"Baguette Pan"),
            new InventoryItem(76872,"Balance Board"),
            new InventoryItem(371787,"Basketball"),
            new InventoryItem(84044,"Battery-Powered Lantern"),
            new InventoryItem(385102,"Beer Mug Set"),
            new InventoryItem(823226,"Bidet"),
            new InventoryItem(595025,"Bike Air Horn"),
            new InventoryItem(206862,"Bike Bungie Net"),
            new InventoryItem(229467,"Bike Mirror"),
            new InventoryItem(997474,"Bike Seat"),
            new InventoryItem(542819,"Bike Stand"),
            new InventoryItem(970854,"Bike Tail Light"),
            new InventoryItem(452712,"Bike Trailer"),
            new InventoryItem(130748,"Blender Bottle"),
            new InventoryItem(497661,"Bolt Action C02 Pistol"),
            new InventoryItem(71788,"Boning Knife"),
            new InventoryItem(19572,"Book Stand"),
            new InventoryItem(170103,"Bookshelf Speakers"),
            new InventoryItem(581823,"Bottle Cage"),
            new InventoryItem(139797,"Bottle Opener"),
            new InventoryItem(34944,"Bottle and Dish Brush"),
            new InventoryItem(951434,"Bread Knife"),
            new InventoryItem(937101,"Brew-In-Mug Device"),
            new InventoryItem(527503,"Brownie Pan"),
            new InventoryItem(715104,"Bundt Pan"),
            new InventoryItem(929939,"Burger Press"),
            new InventoryItem(29846,"Butter Crock"),
            new InventoryItem(39063,"Cake Pan"),
            new InventoryItem(803993,"Can Crusher"),
            new InventoryItem(931995,"Can Opener"),
            new InventoryItem(955551,"Cappuccino and Latte Set"),
            new InventoryItem(815265,"Car Battery Monitor"),
            new InventoryItem(515235,"Carpet Cleaning Machine"),
            new InventoryItem(152741,"Carpet Stain Remover"),
            new InventoryItem(945950,"Cast Iron Pot"),
            new InventoryItem(15729,"Cast-Iron Dutch Oven"),
            new InventoryItem(420010,"Ceramic Cooktop Cleaning Kit"),
            new InventoryItem(143531,"Chef Cleaver Knife"),
            new InventoryItem(893607,"Chef's Knife"),
            new InventoryItem(244911,"Cherry  Olive Pitter"),
            new InventoryItem(877744,"Circuit Tester"),
            new InventoryItem(954547,"Cleaning Pads"),
            new InventoryItem(409787,"Collapsible Strainer"),
            new InventoryItem(281789,"Cookie Dough Scooper"),
            new InventoryItem(796862,"Cork Yoga Block"),
            new InventoryItem(831679,"Corkscrew"),
            new InventoryItem(88257,"Corn Holders"),
            new InventoryItem(234690,"Cupcake Maker"),
            new InventoryItem(777411,"Cupcake Storage Container"),
            new InventoryItem(277501,"Cute Egg Mold"),
            new InventoryItem(84513,"Cutting Board"),
            new InventoryItem(595145,"Cycling Helmet Mirror"),
            new InventoryItem(884938,"Cycling Pedals"),
            new InventoryItem(878795,"Cycling Shorts"),
            new InventoryItem(192719,"DVD+R Discs (8.5 GB)"),
            new InventoryItem(520544,"Danish Dough Whisk"),
            new InventoryItem(578771,"Digital Bathroom Scale"),
            new InventoryItem(594980,"Digital Body Fat Scale"),
            new InventoryItem(748763,"Digital Kitchen Scale"),
            new InventoryItem(107741,"Digital Radio"),
            new InventoryItem(20702,"Digital Shower Radio"),
            new InventoryItem(429280,"Disc"),
            new InventoryItem(997806,"Donut Pan"),
            new InventoryItem(512232,"Drifting Machine"),
            new InventoryItem(796905,"Drinking Glasses"),
            new InventoryItem(91687,"Dry Bag"),
            new InventoryItem(259450,"Dryer vent cleaning system"),
            new InventoryItem(745712,"Drying Rack"),
            new InventoryItem(891433,"Dumbbell Rack"),
            new InventoryItem(374709,"Egg / Bacon Microwave Cooker"),
            new InventoryItem(452859,"Egg Beater"),
            new InventoryItem(148733,"Electric Duster Vacuum"),
            new InventoryItem(390402,"Electric Fondue Maker"),
            new InventoryItem(107781,"Electric Food Slicer"),
            new InventoryItem(113927,"Electric Griddle"),
            new InventoryItem(590089,"Electric Meat Grinder"),
            new InventoryItem(639242,"Electric Rotisserie"),
            new InventoryItem(932108,"Electric Salad Slicer"),
            new InventoryItem(582932,"Electric Scooter"),
            new InventoryItem(241881,"Electric Smoker"),
            new InventoryItem(828697,"Electric Wok"),
            new InventoryItem(920858,"Elevation Training Gear"),
            new InventoryItem(25883,"Energy Chews"),
            new InventoryItem(24860,"Energy Gel"),
            new InventoryItem(835870,"Espresso Spoons"),
            new InventoryItem(555295,"Espresso Tamper"),
            new InventoryItem(621860,"Ethernet Cable"),
            new InventoryItem(21796,"Exercise Bike"),
            new InventoryItem(931117,"Expandable Closet Organizer"),
            new InventoryItem(279854,"Extension Cord"),
            new InventoryItem(320815,"F-Pin Audio/Video Cable"),
            new InventoryItem(668976,"Facial Tissues"),
            new InventoryItem(916785,"Fake TV Burglar Deterrent"),
            new InventoryItem(595252,"Fast, Portable Water Boiler"),
            new InventoryItem(480567,"Fat Separator"),
            new InventoryItem(99641,"Faucet Extender for Kids"),
            new InventoryItem(978234,"Fire Starter"),
            new InventoryItem(876529,"First Aid Kit"),
            new InventoryItem(641348,"Fish Turner"),
            new InventoryItem(375111,"Fishing Reel"),
            new InventoryItem(738632,"Floor Mat"),
            new InventoryItem(253257,"Floorstanding Speaker"),
            new InventoryItem(155989,"Foam Roller"),
            new InventoryItem(977238,"Foldable Laundry Bin"),
            new InventoryItem(144728,"Folding Laundry Basket"),
            new InventoryItem(840026,"Food Bag Sealer Clips"),
            new InventoryItem(97627,"Food Chopper / Pastry Scraper"),
            new InventoryItem(385373,"Food Coloring Set"),
            new InventoryItem(374235,"Food Dehydrator and Jerky Maker"),
            new InventoryItem(890208,"Food Proofer"),
            new InventoryItem(469345,"Food Storage Set"),
            new InventoryItem(40291,"Food Thermometer"),
            new InventoryItem(65900,"Forehead Flashlight"),
            new InventoryItem(96625,"French Press"),
            new InventoryItem(6515,"Frisbee Alternative"),
            new InventoryItem(789877,"Frozen Concoction Maker"),
            new InventoryItem(465271,"Fruit Corer"),
            new InventoryItem(827768,"Fruit and Vegetable Wash"),
            new InventoryItem(222585,"Frying Pan Set"),
            new InventoryItem(703866,"Garage Door Remote"),
            new InventoryItem(231805,"Garlic Peeler"),
            new InventoryItem(281152,"Garlic Press"),
            new InventoryItem(360957,"Glass Bowls w/ Lids"),
            new InventoryItem(90501,"Glass Cleaner"),
            new InventoryItem(615817,"Golf Bag"),
            new InventoryItem(230105,"Golf Balls"),
            new InventoryItem(128407,"Golf Push Cart"),
            new InventoryItem(519573,"Grabber Thing"),
            new InventoryItem(595351,"Grain Mill"),
            new InventoryItem(952729,"Granton Edge Slicing Knife"),
            new InventoryItem(246172,"Grater/Zester"),
            new InventoryItem(79268,"Griddle Pan"),
            new InventoryItem(224678,"Grocery Bag Holder"),
            new InventoryItem(582055,"HD 3D Plasma TV"),
            new InventoryItem(353705,"HD Camcorder"),
            new InventoryItem(265642,"HDMI Cable"),
            new InventoryItem(702891,"HDMI to DVI Cable"),
            new InventoryItem(297389,"Hand Exerciser"),
            new InventoryItem(33198,"Handheld Blender"),
            new InventoryItem(538031,"Hanger Holder"),
            new InventoryItem(848304,"Hanging Laundry Bag"),
            new InventoryItem(125361,"Hanging Pot Rack"),
            new InventoryItem(545211,"Heat-Resistant Utensil Set"),
            new InventoryItem(690622,"Heated Mattress Pad"),
            new InventoryItem(160200,"Hidden Book Shelf"),
            new InventoryItem(143817,"Home Theater Projector"),
            new InventoryItem(147914,"Home Theater Speakers"),
            new InventoryItem(370123,"Huge Portable Hammock"),
            new InventoryItem(173986,"Ice Cream Scooper"),
            new InventoryItem(444323,"Ice Cube Tray"),
            new InventoryItem(774614,"In-Drawer Knife Tray"),
            new InventoryItem(959869,"Indoor Bike Trainer"),
            new InventoryItem(650713,"Inflatable Boat"),
            new InventoryItem(89562,"Inflatable Kayak"),
            new InventoryItem(765403,"Insulated Water Bottle"),
            new InventoryItem(266717,"Iron"),
            new InventoryItem(878047,"Ironing Board Cover"),
            new InventoryItem(734630,"Jar Opener"),
            new InventoryItem(955880,"Jar Sealer"),
            new InventoryItem(404969,"Juicer Thing"),
            new InventoryItem(82413,"Juicing Machine"),
            new InventoryItem(413166,"Kettle"),
            new InventoryItem(598269,"Kid's Trike"),
            new InventoryItem(503284,"Kindle"),
            new InventoryItem(10741,"Knife Block"),
            new InventoryItem(281078,"Knife Sharpener"),
            new InventoryItem(216490,"LED Flashlight"),
            new InventoryItem(714837,"LED Keychain Light"),
            new InventoryItem(167426,"Ladybug Night Light"),
            new InventoryItem(726532,"Laminating Machine Combo Pack"),
            new InventoryItem(578053,"Lapdesk Laptop Speaker"),
            new InventoryItem(394760,"Laptop Stand"),
            new InventoryItem(487950,"Lasagna Trio Pan"),
            new InventoryItem(432557,"Laundry Detergent"),
            new InventoryItem(50706,"Laundry Soda"),
            new InventoryItem(366100,"Laundry Sorter Cart"),
            new InventoryItem(896533,"Legit Dartboard"),
            new InventoryItem(927254,"Letter Opener"),
            new InventoryItem(781913,"Lithium Batteries"),
            new InventoryItem(887215,"Loaf Ban"),
            new InventoryItem(155909,"Longboard Skateboard"),
            new InventoryItem(985632,"Louisville Slugger Baseball Bat"),
            new InventoryItem(88609,"MacGyver Kitchen Machine"),
            new InventoryItem(958833,"Machete Knife"),
            new InventoryItem(254503,"Madeleine Pan"),
            new InventoryItem(179752,"Magnetic Clips"),
            new InventoryItem(128553,"Magnetic Door Blinds"),
            new InventoryItem(467502,"Magnetic Knife Holder"),
            new InventoryItem(172591,"Magnetic Measuring Spoons"),
            new InventoryItem(723504,"Magnifying Glass"),
            new InventoryItem(987711,"Mandoline Slicer"),
            new InventoryItem(20032,"Massage Stick"),
            new InventoryItem(856643,"Measuring Cups"),
            new InventoryItem(168516,"Meat Tenderizer"),
            new InventoryItem(854597,"Mega Plunger"),
            new InventoryItem(968263,"Microwavable Steamer"),
            new InventoryItem(726600,"Microwave Pasta Maker"),
            new InventoryItem(344332,"Milk Frother"),
            new InventoryItem(883276,"Milk Frothing Pitcher"),
            new InventoryItem(310865,"Mini Breadmaker"),
            new InventoryItem(843350,"Mini Exercise Bike"),
            new InventoryItem(465497,"Mini MacGyver"),
            new InventoryItem(737882,"Mirror"),
            new InventoryItem(241243,"Mixer"),
            new InventoryItem(133724,"Mixing Bowl Set"),
            new InventoryItem(357981,"Mortar and Pestle"),
            new InventoryItem(574051,"Mouse Trap"),
            new InventoryItem(553574,"Mug Holder"),
            new InventoryItem(24167,"NFL Official Size Football"),
            new InventoryItem(838250,"Near  Far range Binoculars"),
            new InventoryItem(239213,"Neck/Face Mask"),
            new InventoryItem(259697,"Nonstick Baking Liner"),
            new InventoryItem(339573,"Nonstick Frying Pan"),
            new InventoryItem(225910,"Nonstick Skilet"),
            new InventoryItem(573053,"Oil Pourer"),
            new InventoryItem(273023,"Outdoor Party Game"),
            new InventoryItem(968470,"Oven Glove"),
            new InventoryItem(857734,"Over-the-door Jewelry Rack"),
            new InventoryItem(359049,"Pain Reliever"),
            new InventoryItem(743050,"Paintball Feeder"),
            new InventoryItem(332428,"Paintball Set"),
            new InventoryItem(727693,"Paper Towel Holder"),
            new InventoryItem(405134,"Paring Knife"),
            new InventoryItem(921231,"Pasta Machine"),
            new InventoryItem(31379,"Pedal-less Bike"),
            new InventoryItem(339604,"Pedometer"),
            new InventoryItem(626330,"Peeler"),
            new InventoryItem(515355,"Pepper Grinder"),
            new InventoryItem(822949,"Personal Fan"),
            new InventoryItem(706215,"Pet Food Container"),
            new InventoryItem(18204,"Phone  GPS Mount"),
            new InventoryItem(418759,"Pie Crust Maker"),
            new InventoryItem(740014,"Pineapple Slicer and De-Corer"),
            new InventoryItem(268979,"Ping Pong Paddle"),
            new InventoryItem(957109,"Pitching Machine"),
            new InventoryItem(206518,"Pizza Cutter"),
            new InventoryItem(885876,"Pizza Oven Thing"),
            new InventoryItem(26911,"Pizza Pan"),
            new InventoryItem(758460,"Pizza and Baking Stone"),
            new InventoryItem(816587,"Pizza and Dough Peel"),
            new InventoryItem(44742,"Pizzelle Baker"),
            new InventoryItem(615120,"Pocket Rescusitator"),
            new InventoryItem(736978,"Pocket Water Microfilter"),
            new InventoryItem(923348,"Popsicle Maker"),
            new InventoryItem(591574,"Portable Charcoal Grill"),
            new InventoryItem(277208,"Portable Folding Chair"),
            new InventoryItem(405209,"Portable Gas Grill"),
            new InventoryItem(448222,"Portable Stove"),
            new InventoryItem(193253,"Potato Ricer"),
            new InventoryItem(26347,"Power Strip"),
            new InventoryItem(714476,"Pressure Cooker"),
            new InventoryItem(585856,"Pressure Cooker  Canner"),
            new InventoryItem(719603,"Printer / Scanner / Copier / Fax"),
            new InventoryItem(516855,"Programmable Lego Robot"),
            new InventoryItem(228091,"Programmable Switch"),
            new InventoryItem(666364,"Propulsion Scooter"),
            new InventoryItem(793345,"Pulse Oximeter"),
            new InventoryItem(902919,"Punching Bag"),
            new InventoryItem(239370,"Queen Bed Frame and Box Spring"),
            new InventoryItem(63959,"RCA to RCA Subwoofer Cable"),
            new InventoryItem(102160,"Razor Scooter"),
            new InventoryItem(487185,"Rechargable Bike Headlight"),
            new InventoryItem(311059,"Reusable Grocery Bags"),
            new InventoryItem(757526,"Reusable Produce Bags"),
            new InventoryItem(405977,"Reusable Snack Bag"),
            new InventoryItem(605467,"Rice Cooker"),
            new InventoryItem(220955,"Roller Cart"),
            new InventoryItem(607004,"Rolling Pin"),
            new InventoryItem(492317,"Rolling Step Stool"),
            new InventoryItem(791326,"Rotating Cake Stand"),
            new InventoryItem(377631,"Rotating Turntable"),
            new InventoryItem(336572,"Rowing Machine"),
            new InventoryItem(261924,"Rust  Stain Remover"),
            new InventoryItem(128805,"Salad Spinner"),
            new InventoryItem(924454,"Saucepot and Steamer"),
            new InventoryItem(688945,"Sausage Stuffer"),
            new InventoryItem(197417,"Scissors"),
            new InventoryItem(792364,"Scooter"),
            new InventoryItem(325426,"Scotch Tape"),
            new InventoryItem(163632,"Screen Cleaning Kit"),
            new InventoryItem(596785,"Sheet Gripper"),
            new InventoryItem(962354,"Shoe Dryer"),
            new InventoryItem(378676,"Shoe Rack"),
            new InventoryItem(63285,"Shower Curtain Rings"),
            new InventoryItem(596792,"Showerhead"),
            new InventoryItem(227133,"Silverware Tray"),
            new InventoryItem(398144,"Simple Projector"),
            new InventoryItem(735053,"Skateboard"),
            new InventoryItem(64336,"Skateboard Ramp Kit"),
            new InventoryItem(654136,"Sleeping Bag"),
            new InventoryItem(614226,"Sling-Style Bag"),
            new InventoryItem(717652,"Slip-On Spikes"),
            new InventoryItem(224910,"Slip-free Yoga Towel"),
            new InventoryItem(4950,"Smoke Detector"),
            new InventoryItem(214329,"Snorkel Equipment"),
            new InventoryItem(768860,"Snow Cone Maker"),
            new InventoryItem(127837,"Solo Raft"),
            new InventoryItem(190304,"Soup and Drink Thermos"),
            new InventoryItem(749409,"Soy and Almond Milk Maker"),
            new InventoryItem(68450,"Spaetzle Maker"),
            new InventoryItem(738150,"Speaker Wire"),
            new InventoryItem(46954,"Speedminton Set"),
            new InventoryItem(28523,"Spice Rack"),
            new InventoryItem(601964,"Spinning Organizer"),
            new InventoryItem(784239,"Splitting Axe"),
            new InventoryItem(905076,"Spring Powered BB Gun"),
            new InventoryItem(641910,"Stain  Odor Remover"),
            new InventoryItem(313152,"Stand Mixer Bowl"),
            new InventoryItem(401558,"Steak Knives"),
            new InventoryItem(112791,"Storage Bin"),
            new InventoryItem(922508,"Strainer"),
            new InventoryItem(240526,"Strawberry Stem Remover"),
            new InventoryItem(722925,"Super-Soft, Absorbant Towel"),
            new InventoryItem(165777,"Sushi Maker"),
            new InventoryItem(286612,"Sushi Rice Press"),
            new InventoryItem(518590,"Swiffer Sweeping Cloths"),
            new InventoryItem(933188,"Swim Goggles"),
            new InventoryItem(957340,"Swively Two-Wheeled Skateboard"),
            new InventoryItem(919455,"TV Stand"),
            new InventoryItem(209826,"TV mount"),
            new InventoryItem(845979,"Table Tennis Set"),
            new InventoryItem(981927,"Tactile Keyboard"),
            new InventoryItem(642843,"Teapot"),
            new InventoryItem(938928,"Telescope Sight"),
            new InventoryItem(253874,"Thermos Travel Mug"),
            new InventoryItem(80819,"Tiny Portable Speakers"),
            new InventoryItem(163764,"Toilet bowl ring remover"),
            new InventoryItem(936885,"Tomato Slicer Knife"),
            new InventoryItem(364472,"Tongs"),
            new InventoryItem(874425,"Toothbrush Holder"),
            new InventoryItem(828346,"Tortilla Shell Pans"),
            new InventoryItem(251039,"Toy Hammock"),
            new InventoryItem(255937,"Trash Can"),
            new InventoryItem(411422,"USB Wi-Fi Adapter"),
            new InventoryItem(48067,"Utensil Holder"),
            new InventoryItem(847815,"Utility Cord"),
            new InventoryItem(19401,"Vacuum"),
            new InventoryItem(45002,"Vegetable Brush"),
            new InventoryItem(139219,"Velcro Cable Ties"),
            new InventoryItem(711636,"Vibram Running Shoe"),
            new InventoryItem(791510,"VoIP Phone Adapter"),
            new InventoryItem(692184,"Volleyball"),
            new InventoryItem(613341,"Waffle Maker"),
            new InventoryItem(777182,"Waist Trimmer"),
            new InventoryItem(13279,"Wall-Mounted Spice Rack"),
            new InventoryItem(657376,"Waterproof iPod Shuffle"),
            new InventoryItem(828386,"Weighted Hula Hoop"),
            new InventoryItem(942054,"Whisk"),
            new InventoryItem(841499,"Whiskey Glass"),
            new InventoryItem(243691,"Wine Aerator"),
            new InventoryItem(879597,"Wine Saver"),
            new InventoryItem(522223,"Wireless Camera"),
            new InventoryItem(400368,"Wireless Mouse"),
            new InventoryItem(758769,"Wok"),
            new InventoryItem(175094,"Yoga Mat"),
            new InventoryItem(920567,"iPad Back Cover"),
            new InventoryItem(305144,"iPad Blender"),
            new InventoryItem(743421,"iPhone/iPod Stereo"),
    };
}
