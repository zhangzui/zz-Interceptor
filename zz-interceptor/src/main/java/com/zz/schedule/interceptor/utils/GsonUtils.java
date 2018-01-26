package com.zz.schedule.interceptor.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class GsonUtils {
	/**
	 * 空的 {@code JSON} 数据 - <code>"{}"</code>。
	 */
	private static final String EMPTY_JSON = "{}";
	/**
	 * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
	 */
	private static final String EMPTY_JSON_ARRAY = "[]";
	/**
	 * 默认的 {@code JSON} 日期/时间字段的格式化模式。
	 */
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p>
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code> </strong>
	 *
	 * @param target 目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target) {
		return toJson(target, null, null);
	}

	private static ConcurrentHashMap<String, Gson> gsonCache = new ConcurrentHashMap<String, Gson>();

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p>
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code> </strong>
	 *
	 * @param target      目标对象。
	 * @param targetType  目标对象的类型。
	 * @param datePattern 日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, String datePattern) {
		if (target == null) {
			return EMPTY_JSON;
		}
		if (datePattern == null || datePattern.length() < 1) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = getGsonWithCache(datePattern);
		String result = EMPTY_JSON;
		try {
			if (targetType == null) {
				result = gson.toJson(target);
			} else {
				result = gson.toJson(target, targetType);
			}
		} catch (Exception ex) {
			// logger.warn("目标对象 " + target.getClass().getName() +
			// " 转换 JSON 字符串时，发生异常！", ex);
			if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?>
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			}
		}
		return result;
	}

	private static Gson getGsonWithCache(String datePattern) {
		Gson gson;
		if (!gsonCache.containsKey(datePattern)) {
			Gson tempGson = new GsonBuilder()
					.setDateFormat(datePattern)
					.create();

			// Get the date adapter
			TypeAdapter<Date> dateTypeAdapter = tempGson.getAdapter(Date.class);

			// Ensure the DateTypeAdapter is null safe
			TypeAdapter<Date> safeDateTypeAdapter = dateTypeAdapter.nullSafe();

			GsonBuilder builder = new GsonBuilder()
					.disableHtmlEscaping()
//					.serializeNulls()
					.setDateFormat(datePattern)
					.registerTypeAdapter(Date.class, safeDateTypeAdapter)
//					.registerTypeAdapter(DateTime.class, new DateTimeToLongAdapter())
					.registerTypeAdapter(DateTime.class, new DateTimeAdapter())
					.registerTypeAdapter(BigDecimal.class, new BigDecimalAdapter());
			gsonCache.putIfAbsent(datePattern, builder.create());
		}
		gson = gsonCache.get(datePattern);
		return gson;
	}

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p>
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code> </strong>
	 *
	 * @param target     目标对象。
	 * @param targetType 目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>         要转换的目标类型。
	 * @param json        给定的 {@code JSON} 字符串。
	 * @param token       {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern 日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
		return (T) fromJson(json, token.getType(), datePattern);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定类型的对象
	 *
	 * @param json        给定的 {@code JSON } 字符串
	 * @param type        指定的类型对象
	 * @param datePattern 日期格式模式
	 * @return 给定{@code JSON} 字符串表示的指定类型对象
	 */
	public static Object fromJson(String json, Type type, String datePattern) {
		if (json == null || json.length() < 1) {
			return null;
		}

		if (datePattern == null || datePattern.length() < 1) {
			datePattern = DEFAULT_DATE_PATTERN;
		}

		Gson gson = getGsonWithCache(datePattern);
		try {
			return gson.fromJson(json, type);
		} catch (Exception ex) {
			// logger.error(json + " 无法转换为 " + token.getRawType().getName() +
			// " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定类型的对象
	 *
	 * @param json 给定的 {@code JSON } 字符串
	 * @param type 指定的类型对象
	 * @return 给定{@code JSON} 字符串表示的指定类型对象
	 */
	public static Object fromJson(String json, Type type) {
		return fromJson(json, type, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>   要转换的目标类型。
	 * @param json  给定的 {@code JSON} 字符串。
	 * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>         要转换的目标类型。
	 * @param json        给定的 {@code JSON} 字符串。
	 * @param clazz       要转换的目标类。
	 * @param datePattern 日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (json == null || json.length() < 1) {
			return null;
		}
		if (datePattern == null || datePattern.length() < 1) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = getGsonWithCache(datePattern);
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>   要转换的目标类型。
	 * @param clazz 要转换的目标类。
	 * @param json  给定的 {@code JSON} 字符串。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}
}

class BigDecimalAdapter implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal> {

	@Override
	public BigDecimal deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		if (json == null) {
			return null;
		} else {
			try {
				return json.getAsBigDecimal();
			} catch (Exception e) {
				return null;
			}
		}
	}

	final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0#####################");

	@Override
	public JsonElement serialize(BigDecimal src, Type type, JsonSerializationContext jsonSerializationContext) {
		String value = "";
		if (src != null) {
			value = String.valueOf(DECIMAL_FORMAT.format(src));
		}
		return new JsonPrimitive(value);
	}
}

class DateTimeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

	@Override
	public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		if (json == null) {
			return null;
		} else {
			try {
				return new DateTime().withMillis(TimeUtils.parseDate(TimeUtils.COMMON_FORMAT, json.getAsString()).getTime());
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public JsonElement serialize(DateTime src, Type type, JsonSerializationContext jsonSerializationContext) {
		String value = "";
		if (src != null) {
			value = TimeUtils.formatDate(TimeUtils.COMMON_FORMAT, src.toDate());
		}
		return new JsonPrimitive(value);
	}
}

class DateTimeToLongAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

	@Override
	public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		if (json == null) {
			return null;
		} else {
			try {
				return new DateTime().withMillis(json.getAsLong());
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public JsonElement serialize(DateTime src, Type type, JsonSerializationContext jsonSerializationContext) {
		Long value = null;
		if (src != null) {
			value = src.getMillis();
		}
		return new JsonPrimitive(value);
	}
}