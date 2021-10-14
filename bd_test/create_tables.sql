/****** Object:  Table [dbo].[fa_detalleXfactura]    Script Date: 20/05/2021 19:03:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_detalleXfactura](
	[deta_codigo] [int] IDENTITY(1,1) NOT NULL,
	[fact_codigo] [int] NOT NULL,
	[arti_codigo] [int] NOT NULL,
	[deta_cantidad] [int] NOT NULL,
	[deta_importeST] [numeric](18, 2) NOT NULL,
	[deta_importeU] [numeric](18, 2) NOT NULL,
	[deta_Lista] [nvarchar](2) NULL,
 CONSTRAINT [PK_fa_detalleXfactura] PRIMARY KEY CLUSTERED 
(
	[deta_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fa_factura]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_factura](
	[fact_codigo] [int] IDENTITY(1,1) NOT NULL,
	[fact_numero] [nvarchar](50) NULL,
	[fact_numeron] [int] NULL,
	[fact_punto] [int] NULL,
	[fact_fecha] [smalldatetime] NOT NULL,
	[fact_estado] [nvarchar](1) NOT NULL,
	[clie_codigo] [int] NOT NULL,
	[fact_descuento] [nvarchar](1) NOT NULL,
	[fact_total] [float] NULL,
	[fact_estadocobro] [nvarchar](1) NULL,
	[fact_fechavencimiento] [smalldatetime] NULL,
	[fact_iva] [int] NOT NULL,
	[fact_tipo] [nvarchar](1) NOT NULL,
	[fact_fechaA] [smalldatetime] NULL,
	[fact_emplA] [int] NULL,
	[empl_codigo] [int] NULL,
	[fact_estacion] [nvarchar](1) NULL,
	[fact_cliente] [nvarchar](255) NULL,
	[fact_impresora] [nvarchar](5) NULL,
 CONSTRAINT [PK_fa_factura] PRIMARY KEY CLUSTERED 
(
	[fact_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fa_pagoXfactura]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_pagoXfactura](
	[pfac_codigo] [int] IDENTITY(1,1) NOT NULL,
	[fact_codigo] [int] NOT NULL,
	[pfac_fecha] [smalldatetime] NULL,
	[form_codigo] [int] NOT NULL,
	[pfac_importe] [numeric](18, 2) NOT NULL,
	[pfac_empleado] [int] NOT NULL,
	[pfac_banco] [nvarchar](255) NULL,
	[pfac_Ntransferencia] [nvarchar](255) NULL,
	[pfac_observaciones] [nchar](16) NULL,
	[pfac_fechavenc] [smalldatetime] NULL,
	[pfac_Ncheque] [nvarchar](255) NULL,
	[tarj_codigo] [int] NULL,
	[pfac_Ntarjeta] [nvarchar](255) NULL,
	[pfac_Nlote] [nvarchar](255) NULL,
	[pfac_estado] [nchar](1) NULL,
	[pfac_fechaAsiento] [smalldatetime] NOT NULL,
	[pfac_pasado] [nchar](1) NULL,
	[pfac_tablet] [nchar](1) NULL,
	[pfac_fechaPaso] [smalldatetime] NULL,
 CONSTRAINT [PK_fa_pagoXfactura] PRIMARY KEY CLUSTERED 
(
	[pfac_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fa_pagoXpresupuesto]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_pagoXpresupuesto](
	[ppre_codigo] [int] NOT NULL,
	[pres_codigo] [int] NOT NULL,
	[ppre_fecha] [smalldatetime] NOT NULL,
	[form_codigo] [int] NOT NULL,
	[ppre_importe] [numeric](18, 2) NOT NULL,
	[empl_codigo] [int] NOT NULL,
	[ppre_observaciones] [nvarchar](255) NULL,
	[ppre_estado] [nchar](1) NULL,
	[ppre_fechaAsiento] [smalldatetime] NULL,
	[ppre_pasado] [nchar](1) NULL,
	[ppre_tablet] [nchar](1) NULL,
	[ppre_fechaPaso] [smalldatetime] NULL,
 CONSTRAINT [PK_fa_pagoXpresupuesto] PRIMARY KEY CLUSTERED 
(
	[ppre_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fa_presupuesto]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_presupuesto](
	[pres_codigo] [int] IDENTITY(1,1) NOT NULL,
	[pres_fecha] [smalldatetime] NOT NULL,
	[pres_cliente] [nvarchar](255) NULL,
	[pres_total] [numeric](18, 2) NULL,
	[pres_fechaVencimiento] [smalldatetime] NULL,
	[pres_Observaciones] [nvarchar](255) NULL,
	[pres_estado] [nvarchar](1) NULL,
	[pres_estadoCobro] [nvarchar](1) NULL,
	[clie_codigo] [int] NULL,
 CONSTRAINT [PK_fa_presupuesto] PRIMARY KEY CLUSTERED 
(
	[pres_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fa_presupuestoXdetalle]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fa_presupuestoXdetalle](
	[deps_codigo] [int] IDENTITY(1,1) NOT NULL,
	[pres_codigo] [int] NOT NULL,
	[arti_codigo] [int] NOT NULL,
	[deps_cantidad] [int] NOT NULL,
	[deps_importeST] [numeric](18, 2) NULL,
	[deps_importeU] [numeric](18, 2) NULL,
	[deps_Lista] [nchar](2) NULL,
 CONSTRAINT [PK_fa_presupuestoXdetalle] PRIMARY KEY CLUSTERED 
(
	[deps_codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ge_articulo]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ge_articulo](
	[arti_codigo] [int] NOT NULL,
	[arti_nombre] [nvarchar](255) NOT NULL,
	[arti_imagen] [nvarchar](255) NULL,
	[arti_precioCompra] [numeric](18, 2) NOT NULL,
	[arti_precioLista1] [numeric](18, 2) NULL,
	[arti_precioLista2] [numeric](18, 2) NULL,
	[arti_precioLista3] [numeric](18, 2) NULL,
	[arti_porcLista1] [numeric](9, 2) NULL,
	[arti_porcLista2] [numeric](9, 2) NULL,
	[arti_porcLista3] [numeric](9, 2) NULL,
	[arti_vigente] [nchar](1) NOT NULL,
	[arti_codigoBarra] [nvarchar](255) NULL,
	[arti_envases] [nchar](1) NULL,
	[unid_codigo] [int] NULL,
	[prve_codigo] [int] NULL,
	[arti_inhiL1] [nchar](1) NULL,
	[arti_inhiL2] [nchar](1) NULL,
	[arti_inhiL3] [nchar](1) NULL,
	[enva_codigo] [int] NULL,
	[arti_modificado] [nvarchar](1) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ge_cliente]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ge_cliente](
	[clie_codigo] [int] NOT NULL,
	[clie_nombre] [nvarchar](255) NOT NULL,
	[esta_codigo] [int] NULL,
	[clie_telefono] [nvarchar](50) NOT NULL,
	[clie_email] [nvarchar](255) NULL,
	[clie_domicilio] [nvarchar](255) NULL,
	[clie_localidad] [nvarchar](255) NULL,
	[prov_codigo] [int] NULL,
	[pais_codigo] [int] NULL,
	[clie_CP] [nvarchar](50) NULL,
	[docu_codigo] [int] NULL,
	[clie_Ndocumento] [nvarchar](50) NULL,
	[clie_observaciones] [ntext] NULL,
	[tiva_codigo] [int] NULL,
	[clie_CUIT] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ge_envase]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ge_envase](
	[enva_codigo] [int] NOT NULL,
	[enva_nombre] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_empleado]    Script Date: 20/05/2021 19:03:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_empleado](
	[empl_codigo] [int] NOT NULL,
	[empl_nombre] [nvarchar](255) NOT NULL,
	[empl_telefono] [nvarchar](255) NULL,
	[empl_celular] [nvarchar](50) NULL,
	[empl_email] [nvarchar](255) NULL,
	[empl_usuario] [nvarchar](50) NOT NULL,
	[empl_contrasena] [nvarchar](50) NULL,
	[pues_codigo] [int] NULL,
	[empl_stock] [nchar](1) NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[fa_pagoXfactura] ADD  CONSTRAINT [DF_fa_pagoXfactura_pfac_pasado]  DEFAULT ((0)) FOR [pfac_pasado]
GO
ALTER TABLE [dbo].[ge_cliente] ADD  CONSTRAINT [DF_ge_cliente_esta_codigo]  DEFAULT ((1)) FOR [esta_codigo]
GO
ALTER TABLE [dbo].[ge_cliente] ADD  CONSTRAINT [DF_ge_cliente_docu_codigo]  DEFAULT ((1)) FOR [docu_codigo]
GO
ALTER TABLE [dbo].[ge_cliente] ADD  CONSTRAINT [DF_ge_cliente_tiva_codigo]  DEFAULT ((5)) FOR [tiva_codigo]
GO
ALTER TABLE [dbo].[tb_empleado] ADD  CONSTRAINT [DF_tb_empleado_pues_codigo]  DEFAULT ((0)) FOR [pues_codigo]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0=No muestra, 1=permite ajustes, 2=Permite listado, 3=Permite todo' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_empleado', @level2type=N'COLUMN',@level2name=N'empl_stock'
GO
