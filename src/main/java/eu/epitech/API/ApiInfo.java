package eu.epitech.API;

import com.github.scribejava.apis.TwitterApi;

public class ApiInfo {

	public final String name;
	public final Object scribeApi;
	public final String apiKey;
	public final String apiSecret;
	public final String exampleGetRequest;

	public static ApiInfo TwitterInfo = new ApiInfo(
			"Twitter", TwitterApi.instance(),
			"42AriIXIIgxEFeU9YHTrmdR85",
			"wITToqSU0GOM5u5xeNv7GXbFmffdSDqgZrvtH4Hrr6Hftjtu4M",
			"https://api.twitter.com/1.1/statuses/mentions_timeline.json");

	public ApiInfo(String name, Object scribeApi, String apiKey, String apiSecret, String exampleGetRequest) {
		super();
		this.name = name;
		this.scribeApi = scribeApi;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.exampleGetRequest = exampleGetRequest;
	}
}
