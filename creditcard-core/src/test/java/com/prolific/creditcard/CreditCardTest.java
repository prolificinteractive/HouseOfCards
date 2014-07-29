package com.prolific.creditcard;

import android.os.SystemClock;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.Test;

import static com.prolific.creditcard.CreditCardUtil.AMERICAN_EXPRESS;
import static com.prolific.creditcard.CreditCardUtil.DINERS_CLUB;
import static com.prolific.creditcard.CreditCardUtil.DISCOVER;
import static com.prolific.creditcard.CreditCardUtil.MASTERCARD;
import static com.prolific.creditcard.CreditCardUtil.VISA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreditCardTest {

  static final CreditCardUtil CARD_UTIL = new CreditCardUtil(VISA,
      MASTERCARD,
      DISCOVER,
      AMERICAN_EXPRESS,
      DINERS_CLUB);

  static final Map<CreditCard, String[]> CARD_MAP = new LinkedHashMap<CreditCard, String[]>() {{
    put(VISA, new String[] { "4444444444444448", "4055011111111111", "4012888888881881" });
    put(MASTERCARD, new String[] { "5500005555555559", "5555555555555557", "5111005111051128" });
    put(DISCOVER, new String[] { "6011016011016011", "6011000995500000", "6011000990139424" });
    put(AMERICAN_EXPRESS, new String[] { "371449635398431", "343434343434343", "371144371144376" });
    put(DINERS_CLUB, new String[] { "30569309025904", "36110361103612" });
  }};

  static final Map<int[], Map<String, String>> FORMAT_LIST =
      new LinkedHashMap<int[], Map<String, String>>() {{
        put(new int[] { 4, 6, 5 },
            new LinkedHashMap<String, String>() {{
              put("", "");
              put("3", "3");
              put("34", "34");
              put("345", "345");
              put("3451", "3451");
              put("34512", "3451 2");
              put("345129", "3451 29");
              put("3451298", "3451 298");
              put("34512982", "3451 2982");
              put("345129824", "3451 29824");
              put("3451298247", "3451 298247");
              put("34512982478", "3451 298247 8");
              put("345129824782", "3451 298247 82");
              put("3451298247822", "3451 298247 822");
              put("34512982478229", "3451 298247 8229");
              put("345129824782297", "3451 298247 82297");
              put("3451298247822978", "3451 298247 82297");
            }});
      }};

  @Test public void testFindCardType() {
    for (Map.Entry<CreditCard, String[]> entry : CARD_MAP.entrySet()) {
      for (String s : entry.getValue()) {
        assertEquals(entry.getKey(), CARD_UTIL.findCardType(s));
      }
    }
  }

  @Test public void testVerifyCard() {
    for (CreditCard card : CARD_MAP.keySet()) {
      for (Map.Entry<CreditCard, String[]> entry : CARD_MAP.entrySet()) {
        for (String s : entry.getValue()) {
          boolean valid = CARD_UTIL.validateCard(s, card);
          if (card == entry.getKey()) {
            assertTrue(valid);
          } else {
            assertFalse(valid);
          }
        }
      }
    }
  }

  @Test public void testFormatForViewing() {
    for (Map.Entry<int[], Map<String, String>> entry : FORMAT_LIST.entrySet()) {
      CreditCard card = new TestCard(entry.getKey());
      for (Map.Entry<String, String> e : entry.getValue().entrySet()) {
        String input = e.getKey();
        String expected = e.getValue();
        assertEquals(expected, CARD_UTIL.formatForViewing(input, card));
      }
    }
  }

  @Test public void testExceptions() {
    try {
      CARD_UTIL.formatForViewing(null, null);
      fail();
    } catch (Exception e) {
      assertEquals(InvalidParameterException.class, e.getClass());
      assertEquals("cannot have null credit card number", e.getMessage());
    }

    try {
      CARD_UTIL.formatForViewing("", new TestCard(null));
      fail();
    } catch (Exception e) {
      assertEquals(InvalidParameterException.class, e.getClass());
      assertEquals("cannot have null or empty credit card format", e.getMessage());
    }

    try {
      CARD_UTIL.formatForViewing("", new TestCard(new int[0]));
      fail();
    } catch (Exception e) {
      assertEquals(InvalidParameterException.class, e.getClass());
      assertEquals("cannot have null or empty credit card format", e.getMessage());
    }

    try {
      CARD_UTIL.formatForViewing("", new TestCard(new int[] { -1 }));
      fail();
    } catch (Exception e) {
      assertEquals(InvalidParameterException.class, e.getClass());
      assertEquals("the pattern must contain numbers greater than zero", e.getMessage());
    }

    try {
      CARD_UTIL.formatForViewing("", new TestCard(new int[] { 0 }));
      fail();
    } catch (Exception e) {
      assertEquals(InvalidParameterException.class, e.getClass());
      assertEquals("the pattern must contain numbers greater than zero", e.getMessage());
    }
  }

  static class TestCard implements CreditCard {

    final int[] format;

    TestCard(int[] format) {
      this.format = format;
    }

    @Override public Pattern getTypePattern() { return null; }

    @Override public Pattern getVerifyPattern() { return null; }

    @Override public int[] getFormat() {
      return format;
    }
  }
}
