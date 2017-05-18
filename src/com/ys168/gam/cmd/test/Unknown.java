package com.ys168.gam.cmd.test;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

public class Unknown extends Cmd {

	public Unknown(Context context) {
		super(context);
	}

	@Override
	public Response doExecute() {
		return Response.info("什么");
	}

    @Override
    protected Response beforeExecute() {
        return null;
    }
}
