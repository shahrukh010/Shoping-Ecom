package com.shopme;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.shopme.common.entity.Setting;
import com.shopme.security.oauth.CustomerOAuth2UserService;
import com.shopme.security.oauth.CustomerOauthUser;
import com.shopme.setting.CurrencySettingBag;
import com.shopme.setting.EmailSettingBag;

public class Utility {

	public static String getSiteURL(HttpServletRequest httpServletRequest) {

		String siteURL = httpServletRequest.getRequestURL().toString();

		return siteURL.replace(httpServletRequest.getServletPath(), "");
	}

	public static JavaMailSender parepareMailSender(EmailSettingBag settings) {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(settings.getHost());
		mailSender.setPort(settings.getPort());
		mailSender.setUsername(settings.getUsername());
		mailSender.setPassword(settings.getPassword());

		Properties mailProperties = new Properties();
		mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
		mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());
		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;

	}

	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null)
			return null;

		String customerEmail = null;

		if (principal instanceof UsernamePasswordAuthenticationToken
				|| principal instanceof RememberMeAuthenticationToken) {
			customerEmail = request.getUserPrincipal().getName();
		} else if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
			CustomerOauthUser oauth2User = (CustomerOauthUser) oauth2Token.getPrincipal();
			customerEmail = oauth2User.getEmail();
		}

		return customerEmail;
	}

	public static String formatCurrency(float amount, CurrencySettingBag setting) {

		String symbol = setting.getSymbol();
		String symbolPosition = setting.getSymbolPosition();
		String decimalPointType = setting.getDecimalPointType();
		String thousandPointType = setting.getThousandPointType();
		Integer decimalDigit = setting.getDecimalDigits();

		String pattern = symbolPosition.equals("Before price") ? symbol : "";
		pattern += "###,###";

		if (decimalDigit > 0) {

			pattern += ",";

			for (int count = 1; count <= decimalDigit; count++)
				pattern += "#";
		}

		pattern += symbolPosition.equals("After price") ? symbol : "";
		char thousandSeperator = thousandPointType.equals("POINT") ? ',' : '.';
		char decimalSeperator = decimalPointType.equals("POINT") ? ',' : '.';

//		String pattern = "###,###.##";

		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator(decimalSeperator);
		decimalFormatSymbols.setGroupingSeparator(thousandSeperator);

		DecimalFormat formater = new DecimalFormat(pattern);

		return formater.format(amount);
	}
}
