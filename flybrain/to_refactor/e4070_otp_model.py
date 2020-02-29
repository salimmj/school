import numpy as np

class OTPModel:
    def __init__(
        self,
        b,
        d,
        alpha_1 = 1.57e1,
        beta_1 = 8.00e-1,
        gamma_ron = 1.75e-1,
        alpha_2 = 8.877e1,
        beta_2 = 9.789e1,
        alpha_3 = 2.100e0,
        beta_3 = 1.2e0,
        kappa = 7.089e3,
        c = 7.534e-2,
        p = 1.0,
        I_max = 7.774e1,
        R = 1,
        O = 1,
        N = 21,
        t_step = 1.e-4,
        stimulus = 'parabola',
        concentration = 5.
        ):
        self.b = b
        self.d = d
        self.alpha_1 = alpha_1
        self.beta_1 = beta_1
        self.gamma_ron = gamma_ron
        self.alpha_2 = alpha_2
        self.beta_2 = beta_2
        self.alpha_3 = alpha_3
        self.beta_3 = beta_3
        self.kappa = kappa
        self.c = c
        self.p = p
        self.I_max = I_max
        self.R = R
        self.O = O
        self.N = N
        self.t_step = t_step
        self.stimulus = stimulus
        self.concentration = concentration

    def run(self, t_duration):
        t_range = np.arange(0, t_duration, self.t_step)
        t_steps = len(t_range)
        u_h = self.filter_h(t_range, t_steps)
        u_h_grad = self.filter_h(t_range, t_steps, form = 'gradient')
        v = np.maximum(0., u_h + u_h_grad * self.gamma_ron)
        b = v * 0.0 + self.b
        d = v * 0.0 + self.d
        x_1 = v * 0.0
        x_2 = v * 0.0
        x_3 = v * 0.0
        I = v * 0.0
        for i in range(t_steps-1):
            dx_1 = np.nan_to_num(b[i] * v[i] * (1. - x_1[i]) - d[i] * x_1[i])
            dx_2 = np.nan_to_num(self.alpha_2 * x_1[i] * (1. - x_2[i]) - self.beta_2 * x_2[i] - self.kappa * (x_2[i] ** 0.66) * (x_3[i] ** 0.66))
            dx_3 = np.nan_to_num(self.alpha_3 * x_2[i] - self.beta_3 * x_3[i])
            x_1[i+1] = x_1[i] + self.t_step * dx_1
            x_2[i+1] = x_2[i] + self.t_step * dx_2
            x_3[i+1] = x_3[i] + self.t_step * dx_3
        I = x_2 / (x_2 + self.c ** self.p) * self.I_max
        return u_h, v, x_1, x_2, x_3, I

    def filter_h(self, t_range, t_steps, form='intensity'):
        x_1 = np.zeros((t_steps, self.R, self.O, self.N))
        x_2 = np.zeros((t_steps, self.R, self.O, self.N))
        u = np.zeros((t_steps, self.R, self.O, self.N))
        if self.stimulus == 'constant':
            for i in range(self.N):
                u[:,:,:,i] = u + self.concentration * i
        if self.stimulus == 'step':
            for i in range(self.N):
                u[(t_range>=0.5) * (t_range<=2.5),:,:,i] += 1. + self.concentration * i
        if self.stimulus == 'ramp':
            for i in range(self.N):
                x = (1. + self.concentration * i)/1.8 * (t_range - 0.5) * (t_range>=0.5) * (t_range<=2.3) + (1. + self.concentration * i) * (1 - 5*(t_range - 2.3)) * (t_range>2.3) * (t_range<=2.5)
                x = np.reshape(x, (len(x), 1, 1))
                x = np.repeat(x, self.R, axis = 1)
                x = np.repeat(x, self.O, axis = 2)
                u[:,:,:,i] += x
        if self.stimulus == 'parabola':
            for i in range(self.N):
                x = (1. + self.concentration * i) * ((t_range - 0.5)/1.9)**2.0 * (t_range>=0.5) * (t_range<=2.4)+ (1. + self.concentration * i) * (1 - 10*(t_range - 2.4))**2. * (t_range>2.4) * (t_range<=2.5)
                x = np.reshape(x, (len(x), 1, 1))
                x = np.repeat(x, self.R, axis = 1)
                x = np.repeat(x, self.O, axis = 2)
                u[:,:,:,i] += x
        if form == 'gradient':
            u[:-1] = (u[1:] - u[:-1]) / self.t_step
            u[-1] = u[-1] * 0.
            #u = np.maximum(0., u)
        for i in range(t_steps-1):
            dx_1 = np.nan_to_num(x_2[i])
            dx_2 = np.nan_to_num(self.alpha_1 * self.alpha_1 * (u[i] - x_1[i])- 2 * self.alpha_1 * self.beta_1 * x_2[i])
            x_2[i+1] = x_2[i] + self.t_step * dx_2
            x_1[i+1] = x_1[i] + self.t_step * dx_1
        return x_1

# Example
otp_model = OTPModel(2.,100.)
u_h, v, x_1, x_2, x_3, I = otp_model.run(3.0)