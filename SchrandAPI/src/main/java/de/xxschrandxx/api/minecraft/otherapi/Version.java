package de.xxschrandxx.api.minecraft.otherapi;

public enum Version {
  //UNKNOWN
    UNKNOWN(-1),
  //1.0 https://minecraft.gamepedia.com/1.0#Java_Edition
    v1_0(0),
    v1_0_1(1),
    v1_0_2(2),
    v1_0_3(3),
    v1_0_4(4),
    v1_0_5(5),
    v1_0_6(6),
    v1_0_7(7),
    v1_0_8(8),
    v1_0_9(9),
    v1_0_10(10),
    v1_0_11(11),
    v1_0_12(12),
    v1_0_13(13),
    v1_0_14(14),
    v1_0_15(15),
    v1_0_16(16),
    v1_0_17(17),
  //1.1 https://minecraft.gamepedia.com/1.1#Java_Edition
    v1_1(18),
    v1_1_1(19),
    v1_1_2(20),
    v1_1_3(21),
    v1_1_4(22),
  //1.2 https://minecraft.gamepedia.com/1.2#Java_Edition
    v1_2(23),
    v1_2_1(24),
    v1_2_2(25),
    v1_2_3(26),
    v1_2_4(27),
    v1_2_5(28),
  //1.3 https://minecraft.gamepedia.com/1.3#Java_Edition
    v1_3(29),
    v1_3_1(30),
    v1_3_2(31),
  //1.4 https://minecraft.gamepedia.com/1.4#Java_Edition
    v1_4(32),
    v1_4_1(33),
    v1_4_2(34),
    v1_4_3(35),
    v1_4_4(36),
    v1_4_5(37),
    v1_4_6(38),
    v1_4_7(39),
  //1.5 https://minecraft.gamepedia.com/1.5#Java_Edition
    v1_5(40),
    v1_5_1(41),
    v1_5_2(42),
  //1.6 https://minecraft.gamepedia.com/1.6#Java_Edition
    v1_6(43),
    v1_6_1(44),
    v1_6_2(45),
    v1_6_3(46),
    v1_6_4(47),
  //1.7 https://minecraft.gamepedia.com/1.7#Java_Edition
    v1_7(48),
    v1_7_1(49),
    v1_7_2(50),
    v1_7_3(51),
    v1_7_4(52),
    v1_7_5(53),
    v1_7_6(54),
    v1_7_7(55),
    v1_7_8(56),
    v1_7_9(57),
    v1_7_10(58),
  //1.8 https://minecraft.gamepedia.com/1.8#Java_Edition
    v1_8(59),
    v1_8_1(60),
    v1_8_2(61),
    v1_8_3(62),
    v1_8_4(63),
    v1_8_5(64),
    v1_8_6(65),
    v1_8_7(66),
    v1_8_8(67),
    v1_8_9(68),
  //1.9 https://minecraft.gamepedia.com/1.9#Java_Edition
    v1_9(69),
    v1_9_1(70),
    v1_9_2(71),
    v1_9_3(72),
    v1_9_4(73),
  //1.10 https://minecraft.gamepedia.com/1.10#Java_Edition
    v1_10(74),
    v1_10_1(75),
    v1_10_2(76),
  //1.11 https://minecraft.gamepedia.com/1.11#Java_Edition
    v1_11(77),
    v1_11_1(78),
    v1_11_2(79),
  //1.12 https://minecraft.gamepedia.com/1.12#Java_Edition
    v1_12(80),
    v1_12_1(81),
    v1_12_2(82),
  //1.13 https://minecraft.gamepedia.com/1.13#Java_Edition
    v1_13(83),
    v1_13_1(84),
    v1_13_2(85),
  //1.14 https://minecraft.gamepedia.com/1.14#Java_Edition
    v1_14(86),
    v1_14_1(87),
    v1_14_2(88),
    v1_14_3(89),
    v1_14_4(90),
  //1.15 https://minecraft.gamepedia.com/1.15#Java_Edition
    v1_15(91),
    v1_15_1(92),
    v1_15_2(93),
  //1.16 https://minecraft.gamepedia.com/1.16#Java_Edition
    v1_16(94),
    v1_16_1(95),
    v1_16_2(96),
    v1_16_3(97),
    v1_16_4(98);

    private final int level;

    Version(int level) {
      this.level = level;
    }

    public int i() {
      return level;
    }

  }