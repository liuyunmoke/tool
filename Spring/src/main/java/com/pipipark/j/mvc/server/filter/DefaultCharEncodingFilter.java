package com.pipipark.j.mvc.server.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

import com.pipipark.j.mvc.PPPMvcFilter;
import com.pipipark.j.system.annotation.PPPIndex;
import com.pipipark.j.system.classscan.v2.PPPInitMethod;
import com.pipipark.j.system.core.PPPConstant;

/***
 * 字符集过滤器.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
@PPPIndex(PPPConstant.Index.Highest)
public class DefaultCharEncodingFilter extends CharacterEncodingFilter implements
		PPPMvcFilter, PPPInitMethod<CharacterEncodingFilter> {

	@Override
	public void init_method(CharacterEncodingFilter t) {
		t.setEncoding(PPPConstant.Charset.Default.value());
		t.setForceEncoding(true);
	}

}
