import numpy as np

### Define Constants (same as paper)
alpha_1 = 1.57e1
beta_1 = 8.00e-1
gamma_ron = 1.75e-1
alpha_2 = 8.877e1
beta_2 = 9.789e1
alpha_3 = 2.100e0
beta_3 = 1.2e0
kappa = 7.089e3
c = 7.534e-2
p = 1.0
I_max = 7.774e1

# stimulus = 'constant'

### Define R, O, N
R = 1
O = 1
N = 21

### Define time axis
t_step = 1e-4
t_duration = 15.0
t_range = np.arange(0,t_duration,t_step)

### The helper function for the peri-receptor process
def filter_h(alpha, beta, t_range, R, O, N, stimulus = 'parabola', form = 'intensity', u_const=0):
    t_steps = len(t_range)
    t_step = t_range[1] - t_range[0]
    x_1 = np.zeros((t_steps,R,O,N))
    x_2 = np.zeros((t_steps,R,O,N))
    u = np.zeros((t_steps,R,O,N))
    if stimulus == 'constant':
        for i in range(N):
            u[:,:,:,i] = np.min(u_const, 1. + 5.*i)
    if stimulus == 'step':
        for i in range(N):
            u[(t_range>=0.5) * (t_range<=2.5),:,:,i] += (1. + 20. * i)
    if stimulus == 'ramp':
        for i in range(N):
            x = (1. + 20. * i)/1.8 * (t_range - 0.5) * (t_range>=0.5) * (t_range<=2.3) + (1. + 20. * i) * (1 - 5*(t_range - 2.3)) * (t_range>2.3) * (t_range<=2.5)
            x = np.reshape(x, (len(x), 1, 1))
            x = np.repeat(x, R, axis = 1)
            x = np.repeat(x, O, axis = 2)
            u[:,:,:,i] += x
    if stimulus == 'parabola':
        for i in range(N):
            x = (1. + 20. * i) * ((t_range - 0.5)/1.9)**2.0 * (t_range>=0.5) * (t_range<=2.4) + (1. + 20. * i) * (1 - 10*(t_range - 2.4))**2. * (t_range>2.4) * (t_range<=2.5)
            x = np.reshape(x, (len(x), 1, 1))
            x = np.repeat(x, R, axis = 1)
            x = np.repeat(x, O, axis = 2)
            u[:,:,:,i] += x
    if form == 'gradient':
        u[:-1] = (u[1:] - u[:-1]) / t_step
        u[-1] = u[-1] * 0.
        #u = np.maximum(0., u)
    for i in range(t_steps-1):
        dx_1 = x_2[i,:,:,:]
        dx_2 = alpha * alpha * (u[i,:,:,:] - x_1[i,:,:,:]) - 2 * alpha * beta * x_2[i,:,:,:]
        dx_1[np.isnan(dx_1)] = 0.
        dx_2[np.isnan(dx_2)] = 0.
        x_2[i+1,:,:,:] = x_2[i,:,:,:] + t_step * dx_2
        x_1[i+1,:,:,:] = x_1[i,:,:,:] + t_step * dx_1
    return x_1

### Main callable function for the OTP model
def otp_model(alpha_1, beta_1, R, O, N, t_range, b, d, alpha_2, beta_2, kappa, alpha_3, beta_3, c, p, I_max, filter_h = filter_h, u_const=0, stimulus='constant'):
    u_h = filter_h(alpha_1, beta_1, t_range, R, O, N, stimulus = stimulus, u_const=u_const)
    u_h_grad = filter_h(alpha_1, beta_1, t_range, R, O, N, stimulus = stimulus, form = 'gradient', u_const=u_const)
    v = np.maximum(0., u_h + u_h_grad * gamma_ron)
    b = v * 0.0 + b
    d = v * 0.0 + d
    
    t_steps = len(t_range)
    t_step = t_range[1] - t_range[0]
    x_1 = v * 0.0
    x_2 = v * 0.0
    x_3 = v * 0.0
    I = v * 0.0
    for i in range(t_steps-1):
        dx_1 = b[i,:,:,:] * v[i,:,:,:] * (1. - x_1[i,:,:,:]) - d[i,:,:,:] * x_1[i,:,:,:]
        dx_2 = alpha_2 * x_1[i,:,:,:] * (1. - x_2[i,:,:,:]) - beta_2 * x_2[i,:,:,:] - kappa * (x_2[i,:,:,:] ** 0.66) * (x_3[i,:,:,:] ** 0.66)
        dx_3 = alpha_3 * x_2[i,:,:,:] - beta_3 * x_3[i,:,:,:]
        dx_1[np.isnan(dx_1)] = 0.
        dx_2[np.isnan(dx_2)] = 0.
        dx_3[np.isnan(dx_3)] = 0.
        x_1[i+1,:,:,:] = x_1[i,:,:,:] + t_step * dx_1
        x_2[i+1,:,:,:] = x_2[i,:,:,:] + t_step * dx_2
        x_3[i+1,:,:,:] = x_3[i,:,:,:] + t_step * dx_3
    I = x_2 / (x_2 + c ** p) * I_max
    return u_h, v, x_1, x_2, x_3, I

# Example
u_h, v, x_1, x_2, x_3, I = otp_model(alpha_1, beta_1, R, O, N, t_range, 2., 100., alpha_2, beta_2, kappa, alpha_3, beta_3, c, p, I_max)