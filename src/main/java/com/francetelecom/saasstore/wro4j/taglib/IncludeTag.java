package com.francetelecom.saasstore.wro4j.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public abstract class IncludeTag extends SimpleTagSupport {

	private boolean exploded;
	private List<String> groupNames = new ArrayList<String>();

	@Override
	public void doTag() throws JspException {
		WroConfig config = WroConfig.getInstance();
		try {
			for (String groupName : groupNames) {

				Group group = config.getGroup(groupName);
				if (group == null) {
					throw new ConfigurationException("group '" + groupName
							+ "' was not found.");
				}

				StringBuilder output = new StringBuilder();

				writeBegin(output);

				if (exploded) {
					includeExploded(output, group);
				} else {
					include(output, group);
				}

				writeEnd(output);

				getJspContext().getOut().append(output);

			}
		} catch (Exception e) {
			throw new JspException(e);
		}

	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}

	public void setGroupNames(String strGroupNames) {
		String[] arrGroupNames = strGroupNames.split(",");
		for (String groupName : arrGroupNames) {
			groupNames.add(groupName.trim());
		}
	}

	private void includeExploded(StringBuilder builder, Group group)
			throws ConfigurationException, IOException {
		List<String> files = group.get(getGroupType());
		if (files == null) {
			throw new ConfigurationException(
					"exploded file list for group type '" + getGroupType()
							+ "' for group '" + group.getName()
							+ "' was not found.");
		}

		for (String fileName : files) {
			writeLink(builder, fileName);
		}
	}

	private void include(StringBuilder builder, Group group)
			throws IOException, ConfigurationException {
		String fileName = group.getMinimizedFile(getGroupType());
		if (fileName == null) {
			throw new ConfigurationException("minimized file for group type '"
					+ getGroupType() + "' for group '" + group.getName()
					+ "' was not found.");
		}
		writeLink(builder, fileName);
	}

	private void writeLink(StringBuilder builder, String src)
			throws IOException {
		PageContext context = (PageContext) getJspContext();
		String contextPath = ((HttpServletRequest) context.getRequest())
				.getContextPath();
		String link = String.format(getLinkFormat(), quote(contextPath + src));
		builder.append(link);
	}

	protected abstract String getLinkFormat();

	protected abstract String getGroupType();

	protected abstract String quote(String str);

	protected void writeBegin(StringBuilder builder) throws IOException {

	}

	protected void writeEnd(StringBuilder builder) throws IOException {

	}
}
