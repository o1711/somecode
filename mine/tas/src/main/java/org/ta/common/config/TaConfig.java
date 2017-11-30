package org.ta.common.config;

/**
 * Nov 4, 2011
 */

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.util.TaClassUtil;
import org.ta.common.util.TaPath;
import org.ta.common.util.TaTimeSpan;

/**
 * @author wuzhen
 * 
 */
public class TaConfig {

	public static final Logger LOG = LoggerFactory.getLogger(TaConfig.class);

	public static final String PN_PROPERTIES = "properties";

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd hh.mm.ss");

	public static final String PN_ENVIRONMENT = "environment";//

	protected String name;

	protected List<TaConfig> childList;

	protected Map<String, String> propertyMap = new HashMap<String, String>();

	protected TaConfig parent;

	public TaConfig(String name) {
		this(name, new String[] {}, new String[] {});
	}

	public TaConfig(String name, String pname, String pvalue) {
		this(name, new String[] { pname }, new String[] { pvalue });
	}

	public TaConfig(String name, String[] pnames, String[] pvalues) {
		this.name = name;

		this.childList = new ArrayList<TaConfig>();

		for (int i = 0; i < pnames.length; i++) {
			this.setProperty(pnames[i], pvalues[i]);
		}

	}

	public String getLongName() {
		return this.getProperty("longName");
	}

	public TaConfig init() {

		for (TaConfig c : this.childList) {
			c.init();
		}
		return this;
	}

	public int getChildValueAsInt(String cname, int def) {
		TaConfig c = this.getChild(cname, false);
		if (c == null) {
			return def;
		}
		return c.getValueAsInt(def);
	}

	public long getChildValueAsLong(String cname, long def) {
		TaConfig c = this.getChild(cname, false);
		if (c == null) {
			return def;
		}
		return c.getValueAsLong(def);
	}

	public TaConfig getParent() {
		return this.parent;
	}

	public List<TaConfig> getChildList() {
		return this.childList;
	}

	public List<String> validate(List<String> errors) {
		for (TaConfig c : this.getChildList()) {
			c.validate(errors);
		}
		return errors;
	}

	/**
	 * @return the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setProperties(String prefix, Map<String, String> pts) {
		for (Map.Entry<String, String> en : pts.entrySet()) {
			String key = en.getKey();
			if (!key.startsWith(prefix)) {
				continue;
			}
			String key2 = key.substring(prefix.length());
			String value = en.getValue();
			this.setProperty(key2, value);//
		}
	}

	public void setProperties(Map<String, String> pts) {
		this.propertyMap.putAll(pts);
	}

	public TaConfig property(String name, String value) {
		this.setProperty(name, value);//
		return this;
	}

	public void setProperty(String name, String value) {
		this.propertyMap.put(name, value);
	}

	public boolean getPropertyAsBoolean(String name, boolean def) {
		String pvalue = this.getProperty(name);
		if (pvalue == null) {
			return def;
		}

		return "Y".equalsIgnoreCase(pvalue) || "YES".equalsIgnoreCase(pvalue)
				|| "TRUE".equalsIgnoreCase(pvalue);
	}

	public int getPropertyAsInt(String name) {
		String pvalue = this.getProperty(name);
		if (pvalue == null) {
			throw new TaException("" + this.getPath() + "." + name
					+ " no value set.");
		}
		return Integer.parseInt(pvalue);
	}

	public double getPropertyAsDouble(String name) {
		String pvalue = this.getProperty(name);
		if (pvalue == null) {
			throw new TaException("" + this.getPath() + "." + name
					+ " no value set.");
		}
		return Double.parseDouble(pvalue);
	}

	public TaTimeSpan getPropertyAsTimeSpan(String name, boolean force) {
		TaTimeSpan rt = this.getPropertyAsTimeSpan(name, null);
		if (rt == null && force) {
			throw new TaException("" + this.getPath() + "." + name
					+ " no value set.");
		}
		return rt;
	}

	public TaTimeSpan getPropertyAsTimeSpan(String name) {
		return this.getPropertyAsTimeSpan(name, null);
	}

	public TaTimeSpan getPropertyAsTimeSpan(String name, TaTimeSpan def) {
		String pValue = this.getProperty(name);
		if (pValue == null) {
			return def;
		}
		return TaTimeSpan.valueOf(pValue);
	}

	public int getPropertyAsInt(String name, int def) {
		String pvalue = this.getProperty(name);
		if (pvalue == null) {
			return def;
		}
		return Integer.parseInt(pvalue);
	}

	public Date getPropertyAsDate(String name) {
		String valueS = this.getProperty(name);
		if (valueS == null) {
			return null;
		}
		try {
			return FORMAT.parse(valueS);
		} catch (ParseException e) {
			throw new TaException(e);
		}
	}

	public long getPropertyAsLong(String name, long def) {
		String pvalue = this.getProperty(name);
		if (pvalue == null) {
			return def;
		}
		return Long.parseLong(pvalue);
	}

	public List<String> getPropertyAsCSV(String name) {
		String rtS = this.getProperty(name, "");

		rtS = rtS.trim();

		String[] rtA = new String[] {};

		if (rtS.length() != 0) {
			rtA = rtS.split(",");
		}

		return Arrays.asList(rtA);

	}

	public <T extends TaConfig> T getChild(TaPath path, boolean force) {

		if (path.isAbsolute() && this.parent != null) {
			return (T) this.getRoot().getChild(path, force);
		}

		TaConfig rt = this;

		String[] ps = path.getPath();

		for (int i = 0; i < ps.length; i++) {
			String name = ps[i];
			rt = rt.getChild(name, false);
			if (rt == null) {
				break;
			}
		}
		if (rt == null && force) {
			throw noConfigException(this, ps);
		}
		return (T) rt;
	}

	public TaConfig getRoot() {
		TaConfig rt = this;
		while (rt.parent != null) {
			rt = rt.parent;
		}
		return rt;
	}

	public String getProperty(String name) {
		return this.getProperty(name, false);
	}

	public String getProperty(String name, String def) {
		String rt = this.getProperty(name, false);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	public String getRawProperty(String name) {
		return this.getRawProperty(name, false);
	}

	public String getRawProperty(String name, boolean force) {
		String rt = this.propertyMap.get(name);//
		if (force && rt == null) {
			throw TaConfig.noPropertyException(this, name);
		}
		return rt;
	}

	public File getPropertyAsFile(String name, boolean force) {
		String fname = this.getProperty(name, force);
		if (fname == null) {
			return null;
		}

		File rt = new File(fname);

		if (!rt.exists()) {
			throw new TaException("no this file:" + rt);
		}
		return rt;
	}

	public String getProperty(String name, boolean force) {

		String raw = this.getRawProperty(name, force);

		if (raw == null) {
			return null;
		}

		List<String> vars = this.getVarsInExpression(raw);
		String rt = raw;
		for (String var : vars) {
			String value = this.resolveParamter(var, true);
			if (value != null) {
				rt = rt.replaceAll("\\$\\{" + var + "\\}",
						Matcher.quoteReplacement(value));
			}
		}
		return rt;

	}

	protected List<String> getVarsInExpression(String raw) {

		List<String> rt = new ArrayList<String>();

		String remain = raw;

		while (true) {
			int idx = remain.indexOf("${");
			if (idx < 0) {
				break;
			}
			int idx2 = remain.indexOf("}");
			if (idx2 < 0) {
				throw new TaException("format error:" + raw);
			}

			String var = remain.substring(idx + 2, idx2);
			if (!rt.contains(var)) {
				rt.add(var);
			}

			remain = remain.substring(idx2 + 1);
		}
		return rt;
	}

	private String resolveParamter(String pname, boolean force) {
		return this.resolveParamter(pname, this, force);
	}

	private String resolveParamter(String pname, TaConfig from, boolean force) {
		String rt = null;
		boolean found = false;
		TaConfig ps = this.getChild(PN_PROPERTIES, false);

		if (ps != null) {

			TaConfig c = ps.getChild(pname, false);

			if (c != null) {
				rt = c.getValue(null);//
				found = true;
			}//

		}
		if (found) {
			return rt;
		}

		if (this.parent == null) {

			if (force) {
				throw new TaException("parameter not resolved:" + pname
						+ " for config:" + from.getPath());
			} else {
				LOG.warn("parameter not resolved:" + pname + "for config:"
						+ from.getPath());
				return null;
			}
		}
		return this.parent.resolveParamter(pname, from, force);
	}

	public <T> T newInstance(Class<T> clazz) {
		return newInstance(clazz, new Class[] {}, new Object[] {});
	}

	public <T> T newInstance(Class<T> clazz, Class[] pclass, Object[] params) {
		T rt = TaClassUtil.newInstance(clazz, pclass, params);
		if (rt instanceof TaConfigurable) {
			((TaConfigurable) rt).configure(this);//
		}
		return rt;

	}

	public <T> T newInstance(Class[] pclass, Object[] params) {
		Class<T> cls = this.getPropertyAsClass("class", true);
		return this.newInstance(cls, pclass, params);//
	}

	public <T> T newInstance() {
		return this.newInstance(new Class[] {}, new Object[] {});//
	}

	public <T> Class<T> getPropertyAsClass(String name, Class<T> def) {
		Class<T> rt = this.getPropertyAsClass(name, false);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	public <T> Class<T> getPropertyAsClass(String name, boolean force) {
		try {
			String value = this.getProperty(name, force);
			if (value == null) {
				return null;
			}
			return (Class<T>) Class.forName(value);
		} catch (ClassNotFoundException e) {
			throw new TaException(e);
		}
	}

	public TaConfig addChild(String name, String value) {
		TaConfig cc = new TaConfig(name);
		cc.setProperty("value", value);
		return this.addChild(cc);

	}

	public TaConfig child(TaConfig cf) {
		this.addChild(cf);
		return cf;
	}

	public TaConfig addChild(TaConfig cf) {
		this.childList.add(cf);
		cf.parent = this;
		return this;
	}

	public <T extends TaConfig> T getChild(Class<T> cls, String name,
			boolean force) {
		List<T> l = this.getChildList(cls, name);
		if (l.size() == 0) {
			if (force) {
				throw new TaException(this.getPath() + " has no child:" + name
						+ ",cls:" + cls + ",all child:"
						+ this.getChildNameList());
			}
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			throw new TaException("the child config duplicated in config:"
					+ this.getPath() + ",cls:" + cls + ",name:" + name);
		}
	}

	public List<String> getChildNameList() {
		List<String> rt = new ArrayList<String>();
		for (TaConfig c : this.childList) {
			rt.add(c.getName());//
		}
		return rt;
	}

	public List<TaConfig> getChildList(String name) {
		return this.getChildList(TaConfig.class, name);
	}

	public <T extends TaConfig> List<T> getChildList(Class<T> cls, String name) {
		List<T> cL = this.getChildList(cls);
		List<T> rt = new ArrayList<T>();
		for (T t : cL) {
			if (t.getName().equals(name)) {
				rt.add(t);
			}
		}
		return rt;
	}

	public <T extends TaConfig> List<T> getChildList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (TaConfig c : this.getChildList()) {
			if (cls.isAssignableFrom(c.getClass())) {
				rt.add((T) c);
			}
		}
		return rt;
	}

	public String getTrimedValue(boolean force) {
		String rt = this.getValue(force);
		if (rt == null) {
			return rt;
		}
		return rt.trim();
	}

	public String getValue(boolean force) {
		String rt = this.getValue(null);

		if (rt == null && force) {
			throw new TaException("value not set:" + this.getPath());
		}
		return rt;
	}

	public String getValue(String def) {
		return this.getProperty("value", def);
	}

	public boolean getChildValueAsBoolean(String cname, boolean def) {
		TaConfig rt = this.getChild(cname, false);
		if (rt == null) {
			return def;
		}
		return rt.getPropertyAsBoolean("value", def);
	}

	public String getChildValue(String cname, String def) {
		String rt = this.getChildValue(cname, false);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	public String getChildValue(String cname, boolean force) {
		TaConfig c = this.getChild(cname, false);
		if (force && c == null) {

			throw new TaException("no config:"
					+ cname
					+ " under:"
					+ this.getPath()
					+ (this.getProperty(cname) == null ? ""
							: ",do you mean the property?"));
		}
		if (c == null) {
			return null;
		}
		return c.getValue(force);

	}

	public TaConfig getChild(String name, boolean force) {
		return this.getChild(TaConfig.class, name, force);//
	}

	public int getPropertyAsInt(int def) {
		return this.getPropertyAsInt("value", def);
	}

	public String getPath() {
		if (this.parent == null) {
			return this.name;
		}
		return this.parent.getPath() + "/" + this.name;
	}

	/**
	 * @return
	 */
	public String getAsText() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.propertyMap);
		return sb.toString();
	}

	public StringBuffer dump() {

		StringBuffer rt = new StringBuffer();
		this.dump(rt);
		return rt;
	}

	private void dump(StringBuffer sb) {

		String prefix = "";
		for (int i = 0; i < this.getDepth(); i++) {
			prefix = prefix + "\t";
		}

		sb.append(prefix).append(this.getName());
		for (String k : this.getPropertyNameList()) {
			String v = this.getRawProperty(k);
			sb.append(" ").append(k).append("=").append(v);
			;
		}

		sb.append("\n");

		for (TaConfig config : this.getChildList()) {
			config.dump(sb);
		}
	}

	public int getDepth() {
		if (this.parent == null) {
			return 0;

		}
		return this.parent.getDepth() + 1;
	}

	public static TaException noConfigException(TaConfig parent, String[] path) {
		String pathS = Arrays.asList(path).toString();
		return new TaException("no config with path:" + pathS + ",in parent:"
				+ parent.getPath());
	}

	public static TaException noPropertyException(TaConfig config, String pname) {
		return new TaException("no property of name:" + pname
				+ " in config with path:" + config.getPath() + ",all are:"
				+ config.getPropertyKeyList());
	}

	private List<String> getPropertyKeyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.propertyMap.keySet());
		return rt;
	}

	/**
	 * @return
	 */
	public Properties getChildAsProperties() {
		Properties rt = new Properties();
		for (TaConfig c : this.getChildList()) {
			String key = c.getName();
			String value = c.getProperty("value");
			rt.put(key, value);
		}
		return rt;
	}

	public List<String> getPropertyNameList() {
		List<String> rt = new ArrayList<String>();

		Set<String> pKs = this.propertyMap.keySet();
		rt.addAll(pKs);

		return rt;
	}

	public int getValueAsInt(int def) {
		return this.getPropertyAsInt("value", def);
	}

	public long getValueAsLong(long def) {
		return this.getPropertyAsLong("value", def);
	}

	/**
	 * @param string
	 * @return
	 */
	public TaPath getPropertyAsPath(String pname) {
		String value = this.getProperty(pname);
		if (value == null) {
			return null;
		}
		return TaPath.toPath(value);

	}

	/**
	 * @param string
	 * @param i
	 * @return
	 */
	public TaTimeSpan getChildValueAsTime(String cname, long def) {
		String rateS = this.getChildValue(cname, def + "MS");
		TaTimeSpan rt = TaTimeSpan.valueOf(rateS);
		return rt;
	}

	public <T> T configure(T obj) {
		if (obj instanceof TaConfigurable) {
			((TaConfigurable) obj).configure(this);//
		}
		return obj;
	}

}
