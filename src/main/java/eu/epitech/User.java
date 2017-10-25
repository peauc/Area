package eu.epitech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
	private final String name;
	private final String password;
	private Map<ApiInfo.Name, String> idTokens;
	private ArrayList<Area> areas;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.idTokens = new HashMap<>();
		this.areas = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getIdToken(ApiInfo.Name api) {
		return (this.idTokens.get(api));
	}

	public Map<ApiInfo.Name, String> getIdTokens() {
		return idTokens;
	}

	public void addIdToken(ApiInfo.Name api, String token) {
		if (this.idTokens.get(api) != null) {
			this.idTokens.remove(api);
		}
		this.idTokens.put(api, token);
	}

	public void removeIdToken(ApiInfo.Name api) {
		this.idTokens.remove(api);
	}

	public void setIdTokens(Map<ApiInfo.Name, String> idTokens) {
		this.idTokens = idTokens;
	}

	public ArrayList<Area> getAreas() {
		return areas;
	}

	public void removeArea(Area area) {
		this.areas.remove(area);
	}

	public void addArea(Area area) {
		this.areas.add(area);
	}

	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
}
